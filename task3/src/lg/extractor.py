from helpers import getsoup, getinnertext, preprocess


"""
Wrapper for the LG spec page
"""

SPEC_PAGE = "../../html/lg/lg_v30_specs.html"


def main():
    soup = getsoup(SPEC_PAGE)
    cleansoup = preprocess(soup)
    items = cleansoup.find_all(class_="tech_spec_wrap spec_toggle")
    specs = getspecs(items)
    print(specs)


def getspecs(specs) -> {str: [(str, str)]}:
    dict = {}
    for spec in specs:
        title = getinnertext(spec.find_all(class_="specToggle")[0])
        values = spec.find_all(class_="value")
        titles = spec.find_all(class_="title")
        values = map(lambda val: getinnertext(val), values)
        titles = map(lambda title: getinnertext(title), titles)
        dict[title] = [*zip(titles, values)]
    return dict


if __name__ == '__main__':
    main()
