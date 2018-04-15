from sklearn.feature_extraction.text import CountVectorizer
from util import getsoup, preprocess
from pathlib import Path


def main():
    pos, neg = getpaths()
    pos = [*map(maphtmltotext, pos)]
    vectorizer = CountVectorizer()
    vectorizer.fit_transform(pos)


def getpaths() -> ([str], [str]):
    root = Path("../html")
    dirs = [d for d in root.iterdir() if d.is_dir()]
    positives = []
    negatives = []
    for folder in dirs:
        good = Path(str(folder) + "/good")
        bad = Path(str(folder) + "/bad")

        for f in good.iterdir():
            if f.is_file() and f.name.endswith(".html"):
                positives.append(str(f))

        for f in bad.iterdir():
            if f.is_file() and f.name.endswith(".html"):
                negatives.append(str(f))
    return positives, negatives


def maphtmltotext(path: str) -> str:
    soup = getsoup(path)
    pptxt = preprocess(soup)
    # return [txt for txt in pptxt.split(' ') if len(txt) > 0]
    return pptxt


if __name__ == '__main__':
    main()
