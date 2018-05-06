from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.model_selection import KFold, train_test_split
from sklearn.feature_selection import mutual_info_classif

from sklearn.naive_bayes import GaussianNB
from sklearn.tree import DecisionTreeClassifier
from sklearn.svm import SVC
from sklearn.linear_model import LogisticRegression
from sklearn.neural_network import MLPClassifier

from sklearn.metrics import accuracy_score
from sklearn.metrics import classification_report
from sklearn.metrics import confusion_matrix

from util import getsoup, preprocess
from pathlib import Path
import pandas as pd
from time import time


def main():
    _t0 = time()
    t0 = time()
    X, y = getdataset()
    print('Time taken to build dataset: %0.3fs' % (time() - t0))
    print('# of Features: %i' % len(X[0]))
    print('-'*50+'\n')

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.4, random_state=42)

    names = ['Naïve Bayes', 'Decision Tree', 'SVM', 'Logistic Regression', 'Multilayer Perceptron']
    clfs = [
        GaussianNB(),
        DecisionTreeClassifier(),
        SVC(kernel='linear', C=1),
        LogisticRegression(),
        MLPClassifier(max_iter=700, alpha=1)
    ]

    for clf_name, clf in zip(names,clfs):
        t0 = time()
        clf.fit(X_train, y_train)
        ypred = clf.predict(X_test)
        print(clf_name)
        print('Training time: {0:0.3f}s'.format((time() - t0)))
        print('Accuracy: {0:.2%}'.format(clf.score(X_test, y_test)))
        tn, fp, fn, tp = confusion_matrix(y_test, ypred).ravel()
        print('Confusion Matrix:')
        print('\t-\t+')
        print('-\t{0}\t{1}'.format(tn, fp))
        print('+\t{0}\t{1}'.format(fn, tp))
        print('Classification report:')
        print(classification_report(ypred, y_test))
        print('-'*50+'\n')

    print('Total running time: {0:.3f}s'.format(time() - _t0))


# Compute metrics for the classifier using cross-validation
def computemetrics(clf):
    return None


def getstatistics(actual, predicted):
    size = len(actual)
    mistakes = 0
    hits = 0
    for v1, v2 in [*zip(actual, predicted)]:
        if v1 == v2:
            hits += 1
        else:
            mistakes += 1
    return hits/size, mistakes/size


# TODO Use HashingVectorizer instead of TfidfVectorizer ?
def getdataset(max_feats=None, rankbyinfogain=False):
    (pos, neg) = getpaths()
    positives = [*map(maphtmltotext, pos)]
    negatives = [*map(maphtmltotext, neg)]

    documents = positives + negatives
    Y = [1 for _ in range(100)] + [0 for _ in range(100)]

    if rankbyinfogain:
        X = featureselection(positives, negatives, Y[:100], Y[100:200], normalize=False)
        return X, Y

    # savetocsv(documents, Y)

    vectorizer = TfidfVectorizer(
        stop_words='english',
        strip_accents='unicode',
        max_features=max_feats
    )
    vectorizer.fit(documents)

    X = vectorizer.transform(documents).toarray()

    return X, Y


def featureselection(x_positives, x_negatives, y_positives, y_negatives, normalize=False):
    cv1 = CountVectorizer(stop_words='english', min_df=2, analyzer='word', token_pattern=r'[a-zA-Z][a-zA-Z][a-zA-Z]*')
    x_pos = cv1.fit_transform(x_positives)

    cv2 = CountVectorizer(stop_words='english', min_df=2, analyzer='word', token_pattern=r'\w\w+')
    x_neg = cv2.fit_transform(x_negatives)

    pos_features = dict(zip(cv1.get_feature_names(), mutual_info_classif(x_pos, y_positives, discrete_features=True)))
    neg_features = dict(zip(cv2.get_feature_names(), mutual_info_classif(x_neg, y_negatives, discrete_features=True)))

    best = sorted(pos_features, key=pos_features.get, reverse=True)[:1000]
    worst = sorted(neg_features, key=neg_features.get, reverse=True)[:1000]
    # print('#'*20)
    # print('Best good features')
    # print(best)
    # print('Best bad features')
    # print(worst)
    # print('#'*20)

    best_cv = TfidfVectorizer(norm=('l1' if normalize else None))
    best_cv.fit(best)
    x = best_cv.transform(x_positives + x_negatives).toarray()
    print('end of feature selection')
    print('#'*20,)
    return x


def savetocsv(docs, y):
    df = pd.DataFrame(data=docs, columns=["page"])
    df.to_csv('dataset.csv')


def getpaths() -> ([str], [str]):
    root = Path("../html")
    dirs = [d for d in root.iterdir() if d.is_dir()]
    positives = []
    negatives = []
    for folder in dirs:
        good = Path(str(folder) + "/good")
        bad = Path(str(folder) + "/bad")

        for f in good.iterdir():
            if f.is_file() and f.name.endswith("html") or f.name.endswith("htm"):
                positives.append(str(f))

        for f in bad.iterdir():
            if f.is_file() and f.name.endswith("html") or f.name.endswith("htm"):
                negatives.append(str(f))
    return positives, negatives


def maphtmltotext(path: str) -> str:
    soup = getsoup(path)
    pptxt = preprocess(soup)
    # return [txt for txt in pptxt.split(' ') if len(txt) > 0]
    return pptxt


if __name__ == '__main__':
    main()
