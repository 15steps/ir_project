from sklearn.feature_extraction.text import CountVectorizer
from util import getsoup, preprocess


PATH = "../../task3/html/apple/iphone_specs.html"


def main():
    soup = getsoup(PATH)
    pptxt = preprocess(soup)
    count_vec = CountVectorizer()
    dict = {}
    for word in str.split(pptxt, " "):
        if len(word) > 1:
            if word not in dict:
                dict[word] = 0
            else:
                dict[word] += 1
    bag = {word: count for word, count in dict.items() if count > 0}
    print(count_vec.fit_transform([bag]))
    print(bag)


if __name__ == '__main__':
    main()
