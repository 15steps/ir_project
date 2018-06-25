from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.model_selection import cross_validate, train_test_split
from pathlib import Path
from sklearn.metrics import confusion_matrix
from itertools import chain
from time import time
from classifier import getdataset
from sklearn.neural_network import MLPClassifier
from sklearn.metrics import accuracy_score,precision_score, recall_score

from sklearn.naive_bayes import GaussianNB
from sklearn.tree import DecisionTreeClassifier
from sklearn.svm import SVC
from sklearn.linear_model import LogisticRegression
from sklearn.neural_network import MLPClassifier


def main():
    print('Building dataset...')
    X, y, vec = getdataset(rankbyinfogain=True)
    metrics = ['accuracy', 'precision', 'recall']

    clfs = [
        GaussianNB(),
        DecisionTreeClassifier(),
        SVC(kernel='linear', C=1),
        LogisticRegression(),
        MLPClassifier(max_iter=1000, alpha=1)
    ]
    names = ['Naïve Bayes', 'Decision Tree', 'SVM', 'Logistic Regression', 'Multilayer Perceptron']

    for name, clf in zip(names, clfs):
        print('Fitting {0} classifier...'.format(name))
        clf.fit(X, y)
    print('Finished training classifiers!')
    runclassf(clfs, names, vec)
    # print('--- Classification job started ---')
    # y_pred = runclassf(clf, vec)
    # pos, neg = metric(y_pred)
    # print('Relevants: {0:.2%}'.format(pos))
    # print('Not Relevants: {0:.2%}'.format(neg))


def runclassf(clfs, names, vec):
    print('*'*100)
    dirs = getpaths()
    # pages = list(chain(dirs))

    for site_folder in dirs:
        docs = []
        n_pages = 0
        for file in site_folder.iterdir():
            if file.name.endswith('html'):
                n_pages += 1
                with open(str(file), encoding='utf-8') as f:
                    # print('Transforming file: {0}'.format(file.name))
                    content = f.read()
                    docs.append(content)
        X = vec.transform(docs).toarray()
        print(f'-------- Site: {site_folder.name} ----------')
        for clf, name in zip(clfs, names):
            classify(X, n_pages, clf, name)


def classify(X, n_pages, clf, clf_name):
    t0 = time()
    print(f'Predicting results for {clf_name}')
    y_true = [1 for _ in range(n_pages)]
    y_pred = clf.predict(X)

    # tn, fp, fn, tp = confusion_matrix(y_true, y_pred).ravel()
    # print('MLP Confusion Matrix:')
    # print('\t-\t+')
    # print('-\t{0}\t{1}'.format(tn, fp))
    # print('+\t{0}\t{1}'.format(fn, tp))
    # print('\n\n')

    print('Accuracy: {0:.2%}'.format(accuracy_score(y_true, y_pred)))
    print('Precision: {0:.2f}'.format(precision_score(y_true, y_pred)))
    print('Recall: {0:.2f}'.format(recall_score(y_true, y_pred)))

    print('Total running time fo prediction: {0:.2f}s'.format(time() - t0))
    print('*' * 100)


def getpaths(root='../../task1/files_new'):
    path = Path(root)
    fs = []
    for folder in path.iterdir():
        site = folder.name
        if site != 'robots' and folder.is_dir():
            p = str(folder) + '/'
            files = Path(p)
            htmls = [*filter(lambda f: str.endswith(f.name, 'html'), list(files.iterdir()))]
            fs.append(files)
    return fs


if __name__ == '__main__':
    main()
