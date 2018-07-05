from bs4 import BeautifulSoup, NavigableString, Tag, Comment
import re

"""
Helper functions
"""


def preprocess(soup: BeautifulSoup) -> str:
    # filtered = removetags(soup)
    # text = getalltext(filtered)

    specs = soup.find_all(find_specs)
    specs = ' '.join([*map(lambda tag: tag.getText(), specs)])

    texts = soup.findAll(text=True)
    visible_text = filter(visible_tag, texts)
    text = u" ".join(t.strip() for t in visible_text)

    return str.lower(text)


def getalltext(soup: BeautifulSoup) -> object:
    """
    Returns all text from an HTML soup
    :param soup: Beautifulsoup document
    :return: Text without HTML tags
    """
    # if isinstance(soup, NavigableString):
    #     return str(soup)
    # txt = ''.join(soup.find_all(text=True))
    # body = soup.find('body').getText()
    # title = soup.find('title').getText()
    # return re.sub(r'[\n\t\r\,]', ' ', body + title)
    return re.sub(r'[\n\t\r\,]', ' ', soup.getText())


def removetags(sp: BeautifulSoup) -> BeautifulSoup:
    """
    Removes tags not useful for us such as
    the script and comment tags
    :return: Soup with no script and comment tags
    """
    scripts = sp.find_all("script")
    styles = sp.find_all("style")
    noscript = sp.find_all("noscript")

    for tag in [*scripts, *styles, *noscript]:
        tag.extract()
    return sp


def find_specs(tag: Tag):
    rx = re.compile(r'.*(spec|feature).*')
    for attr, val in tag.attrs.items():
        if rx.search(str(val)):
            return True
    return False


def visible_tag(element):
    if element.parent.name in ['style', 'script', 'head', 'title', 'meta', '[document]']:
        return False
    if isinstance(element, Comment):
        return False
    return True


def getsoup(path: str) -> BeautifulSoup:
    """
    Returns a soup from a file
    :param path: Path to file
    :return: Soup document
    """
    with open(path, encoding="utf-8") as f:
        return BeautifulSoup(f, "lxml")