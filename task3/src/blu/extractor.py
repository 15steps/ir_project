from helpers import getinnertext, preprocess, getsoup
import re

PATH = "../../html/blu/blu_specs.html"


"""
Extracts data from a spec page section the Blu page
"""


def main():
    soup = getsoup(PATH)
    cleansoup = preprocess(soup)
    specs = getspecs(cleansoup)
    print(specs)


def getspecs(soup) -> {}:
    dict = {}
    specs = soup.find_all(class_="blu-pg-sec")
    for spec in specs:
        txt = spec.find_all(class_=re.compile("^blu-pg-txt"))
        if len(txt) > 0:
            title = getinnertext(txt[0].find_all(class_="blu-pg-sec-ttl")[0])
            description = ''.join(map(lambda node: getinnertext(node), txt[0].find_all("p")))
            dict[title] = description
    return dict


if __name__ == '__main__':
    main()