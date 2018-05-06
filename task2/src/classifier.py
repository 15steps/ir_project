from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.model_selection import KFold, train_test_split
from sklearn.feature_selection import mutual_info_classif

from sklearn.naive_bayes import GaussianNB
from sklearn.tree import DecisionTreeClassifier
from sklearn import svm
from sklearn.linear_model import LogisticRegression
from sklearn.neural_network import MLPClassifier

from util import getsoup, preprocess
from pathlib import Path
import pandas as pd
from time import time


def main():
    t0 = time()
    X, y = getdataset(rankbyinfogain=True)
    print('Time taken to build dataset: %0.3fs' % (time() - t0))
    print('# of Features: %i' % len(X[0]))
    print('-'*20)

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.4, random_state=42)

    gnb = GaussianNB()  # Naive Bayes
    t0 = time()
    gnb.fit(X_train, y_train)
    print('Naïve Bayes training time: %0.3fs' % (time() - t0))

    t0 = time()
    dt = DecisionTreeClassifier()  # Decision Tree
    dt.fit(X_train, y_train)
    print('Decision Tree training time: %0.3fs' % (time() - t0))

    t0 = time()
    supportvm = svm.SVC()  # SVM
    supportvm.fit(X_train, y_train)
    print('SVM training time: %0.3fs' % (time() - t0))

    t0 = time()
    logisticclf = LogisticRegression()  # Logistic Regression
    logisticclf.fit(X_train, y_train)
    print('Logisitic Regression training time: %0.3fs' % (time() - t0))

    t0 = time()
    mlp = MLPClassifier(max_iter=500)  # Multilayer perceptron
    mlp.fit(X_train, y_train)
    print('MLP training time: %0.3fs' % (time() - t0))

    dtpred = dt.predict(X_test)  # decision tree prediction
    nbpred = gnb.predict(X_test)  # naive bayes prediction
    svmpred = supportvm.predict(X_test)  # SVM prediction
    logisticpred = logisticclf.predict(X_test)
    mlppred = mlp.predict(X_test)

    print('-'*20)
    print('Decision Tree: ({0[0]:.2%}, {0[1]:0.2%})'.format(getstatistics(y_test, dtpred)))
    print('Naïve Bayes: ({0[0]:.2%}, {0[1]:0.2%})'.format(getstatistics(y_test, nbpred)))
    print('SVM: ({0[0]:.2%}, {0[1]:0.2%})'.format(getstatistics(y_test, svmpred)))
    print('Logistic: ({0[0]:.2%}, {0[1]:0.2%})'.format(getstatistics(y_test, logisticpred)))
    print('MLP: ({0[0]:.2%}, {0[1]:0.2%})'.format(getstatistics(y_test, mlppred)))


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
        X = featureselection(positives, negatives, Y[:100], Y[100:200])
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
