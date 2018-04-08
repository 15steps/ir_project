from helpers import getsoup, getinnertext, preprocess
import re

PHONE_SPECS_PAGE = "../../html/apple/iphone_specs.html"


def main():
    soup = getsoup(PHONE_SPECS_PAGE)
    cleansoup = preprocess(soup)
    specs = cleansoup.find_all(class_="techspecs-section")
    # print(specs)
    specsdict = getspecs(specs)
    print(specsdict)

def getspecs(specs):
    dict = {}
    for spec in specs:
        header = spec.find_all(class_="techspecs-rowheader")
        items = spec.find_all("li")
        headertxt = header[0].text
        attrbs = [*map(lambda sp: getinnertext(sp), items)]
        dict[headertxt] = attrbs
    return dict


if __name__ == '__main__':
    main()