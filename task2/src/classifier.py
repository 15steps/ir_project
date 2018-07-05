from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_extraction.text import CountVectorizer, HashingVectorizer
from sklearn.model_selection import cross_validate, train_test_split
from sklearn.feature_selection import mutual_info_classif

from sklearn.naive_bayes import GaussianNB
from sklearn.tree import DecisionTreeClassifier
from sklearn.svm import SVC
from sklearn.linear_model import LogisticRegression
from sklearn.neural_network import MLPClassifier

from util import getsoup, preprocess
from pathlib import Path
from time import time

def main():
    _t0 = time()
    t0 = time()
    X, y, vec = getdataset(rankbyinfogain=True)
    print('Time taken to build dataset: %0.3fs' % (time() - t0))
    print('# of Features: %i' % len(X[0]))
    print('-'*50+'\n')

    return

    # X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.4, random_state=42)
    names = ['Naïve Bayes', 'Decision Tree', 'SVM', 'Logistic Regression', 'Multilayer Perceptron']

    clfs = [
        GaussianNB(),
        DecisionTreeClassifier(),
        SVC(kernel='linear', C=1),
        LogisticRegression(),
        MLPClassifier(max_iter=1000, alpha=1)
    ]

    for clf_name, clf in zip(names, clfs):
        metrics = ['accuracy', 'precision', 'recall']
        scores = cross_validate(clf, X, y, scoring=metrics, cv=5, return_train_score=False, n_jobs=-1)
        scores = dict(map(lambda item: (item[0], mean(item[1])), scores.items()))

        print(clf_name)
        print('fit_time: {0:.3}s'.format(scores['fit_time']))
        print('accuracy: {0:.2%}'.format(scores['test_accuracy']))
        print('precision: {0:.3f}'.format(scores['test_precision']))
        print('recall: {0:.3f}'.format(scores['test_recall']))

        # Confusion Matrix
        # tn, fp, fn, tp = confusion_matrix(y_test, ypred).ravel()
        # print('Confusion Matrix:')
        # print('\t-\t+')
        # print('-\t{0}\t{1}'.format(tn, fp))
        # print('+\t{0}\t{1}'.format(fn, tp))
        print('-'*50+'\n')

    print('Total running time: {0:.3f}s'.format(time() - _t0))


def mean(l):
    return sum(l)/len(l)


# TODO Use HashingVectorizer instead of TfidfVectorizer ?
def getdataset(max_feats=None, rankbyinfogain=False):
    (pos, neg) = getpaths()
    positives = [*map(maphtmltotext, pos)]
    negatives = [*map(maphtmltotext, neg)]

    documents = positives + negatives
    Y = [1 for _ in range(100)] + [0 for _ in range(100)]

    if rankbyinfogain:
        X, vec = featureselection(positives, negatives, Y[:100], Y[100:200])
        return X, Y, vec

    vectorizer = TfidfVectorizer(
        stop_words='english',
        strip_accents='unicode',
        max_features=max_feats
    )
    vectorizer.fit(documents)

    X = vectorizer.transform(documents).toarray()

    return X, Y, vectorizer


def featureselection(x_positives, x_negatives, y_positives, y_negatives):
    cv1 = CountVectorizer(stop_words='english', min_df=2, analyzer='word', token_pattern=r'[a-zA-Z][a-zA-Z][a-zA-Z]*')
    x_pos = cv1.fit_transform(x_positives)

    cv2 = CountVectorizer(stop_words='english', min_df=2, analyzer='word', token_pattern=r'\w\w+')
    x_neg = cv2.fit_transform(x_negatives)

    pos_features = dict(zip(cv1.get_feature_names(), mutual_info_classif(x_pos, y_positives, discrete_features=True)))
    neg_features = dict(zip(cv2.get_feature_names(), mutual_info_classif(x_neg, y_negatives, discrete_features=True)))

    cv = CountVectorizer(stop_words='english', min_df=2, analyzer='word', token_pattern=r'[a-zA-Z][a-zA-Z][a-zA-Z]*')
    X = cv.fit_transform(x_positives + x_negatives)
    Y = y_positives + y_negatives
    feats = dict(zip(cv.get_feature_names(), mutual_info_classif(X, Y, discrete_features=True)))

    best = sorted(pos_features, key=pos_features.get, reverse=True)[:1000]
    worst = sorted(neg_features, key=neg_features.get, reverse=True)[:1000]

    bbest = sorted(feats, key=feats.get, reverse=True)[:1000]

    print('#'*50)
    print('Best good features')
    print(bbest)
    print('#'*50)
    # print('Best bad features')
    # print(worst)
    # print('#'*20)

    best_cv = CountVectorizer()
    best_cv.fit([*best, *worst])
    # best_cv.fit(best)
    x = best_cv.transform(x_positives + x_negatives).toarray()

    print('end of feature selection')
    print('#'*20,)
    return x, best_cv


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
