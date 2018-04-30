from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import GaussianNB
from sklearn.model_selection import KFold, train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn import svm
from util import getsoup, preprocess
from pathlib import Path
from random import shuffle
import pandas as pd


def main():
    X, y = getdataset()
    # X, y = shuffledataset(X, y)

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.4, random_state=42)

    gnb = GaussianNB()  # Naive Bayes
    gnb.fit(X_train, y_train)

    dt = DecisionTreeClassifier() # Decision Tree
    dt.fit(X_train, y_train)

    supportvm = svm.SVC() # SVM
    supportvm.fit(X_train, y_train)

    dtpred = dt.predict(X_test)  # decision tree prediction
    nbpred = gnb.predict(X_test)  # naive bayes prediction
    svmpred = supportvm.predict(X_test) # SVM prediction

    print('Decision Tree')
    print(getstatistics(y_test, dtpred))
    print('Naive Bayes')
    print(getstatistics(y_test, nbpred))
    print('SVM')
    print(getstatistics(y_test, svmpred))


def getstatistics(actual, predicted):
    size = len(actual)
    mistakes = 0
    hits = 0
    for v1, v2 in [*zip(actual, predicted)]:
        if v1 == v2:
            hits += 1
        else:
            mistakes += 1
    return hits/size * 100, mistakes/size * 100


def shuffledataset(X, y):
    dset = [*zip(X, y)] # array of (document, class)
    shuffle(dset)
    [_X, _y] = zip(*dset) # unzips dset
    return list(_X), list(_y)


# TODO Use HashingVectorizer instead of TfidfVectorizer
def getdataset():
    (pos, neg) = getpaths()
    positives = [*map(maphtmltotext, pos)]
    negatives = [*map(maphtmltotext, neg)]

    documents = positives + negatives
    Y = [1 for _ in range(100)] + [0 for _ in range(100)]

    # savetocsv(documents, Y)

    vectorizer = TfidfVectorizer()
    vectorizer.fit(documents)

    X = vectorizer.transform(documents).toarray()

    return X, Y


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
