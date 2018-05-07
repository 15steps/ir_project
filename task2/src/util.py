from bs4 import BeautifulSoup, NavigableString
import re

"""
Helper functions
"""


def preprocess(soup: BeautifulSoup) -> str:
    filtered = removetags(soup)
    text = getalltext(filtered)
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


def getsoup(path: str) -> BeautifulSoup:
    """
    Returns a soup from a file
    :param path: Path to file
    :return: Soup document
    """
    with open(path, encoding="utf-8") as f:
        return BeautifulSoup(f, "lxml")