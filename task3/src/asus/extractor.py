from helpers import getsoup, preprocess, getinnertext


"""
Retrieves structured data from the Asus spec page
"""


SPEC_PAGE = "../../html/asus/asus_spec.html"


def main():
    soup = getsoup(SPEC_PAGE)
    cleansoup = preprocess(soup)
    getspecs(cleansoup)


def getspecs(soup):
    specs = soup.find_all(class_="spec")
    for spec in specs:
        print(spec.prettify())


if __name__ == '__main__':
    main()
