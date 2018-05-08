from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.model_selection import cross_validate
from sklearn.feature_selection import mutual_info_classif

from sklearn.naive_bayes import GaussianNB
from sklearn.tree import DecisionTreeClassifier
from sklearn.svm import SVC
from sklearn.linear_model import LogisticRegression
from sklearn.neural_network import MLPClassifier

from sklearn.metrics import confusion_matrix

from util import getsoup, preprocess
from pathlib import Path
from time import time
from bs4 import BeautifulSoup, Tag
import re


def main():
    pos, neg = getpaths()
    pos = [*map(lambda file: BeautifulSoup(open(file, encoding='utf-8'), 'lxml'), pos)]
    neg = [*map(lambda file: BeautifulSoup(open(file, encoding='utf-8'), 'lxml'), neg)]

    pos_docs = []
    for page in pos:
        _as = page.find_all(f)
        temp = ' '.join([*map(getrelevantinfo, _as)])
        pos_docs.append(temp)

    neg_docs = []
    for page in pos:
        _as = page.find_all(f)
        temp = ' '.join([*map(getrelevantinfo, _as)])
        neg_docs.append(temp)

    X = [*pos_docs, *neg_docs]
    Y = [1 for _ in range(100)] + [0 for _ in range(100)]

    cv = CountVectorizer(stop_words='english', min_df=2, analyzer='word', token_pattern=r'[a-zA-Z][a-zA-Z][a-zA-Z]*')
    _X = cv.fit_transform(X)

    selfeat = dict(zip(cv.get_feature_names(), mutual_info_classif(_X, Y, discrete_features=True)))
    print(sorted(selfeat, reverse=True))


def getrelevantinfo(t: Tag):
    clazz = t.get('href')
    link = ''.join([] if not clazz else clazz)
    link = re.sub(r'^.*\.com', '', link)
    link = re.sub(r'/', ' ', link)
    print(link)
    txt = t.text + ' ' + link
    return re.sub(r'\s+', ' ', txt)
    # print(t.get('class'))


def f(t: Tag):
    return t.name == 'a' and len(list(t.children)) <= 1


def getpaths(root_path='html') -> ([str], [str]):
    root = Path('../{0}'.format(root_path))
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


if __name__ == '__main__':
    main()
