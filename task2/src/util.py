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
    if isinstance(soup, NavigableString):
        return str(soup)
    txt = ''.join(soup.find_all(text=True))
    return re.sub(r'[\n\t\r\,]', ' ', txt)


def removetags(soup: BeautifulSoup) -> BeautifulSoup:
    """
    Removes tags not useful for us such as
    the script and comment tags
    :return: Soup with no script and comment tags
    """
    sp = soup.find_all("body")[0];
    # sp = soup
    scripts = sp.find_all("script")
    for script in scripts:
        script.extract()
    return sp


def getsoup(path: str) -> BeautifulSoup:
    """
    Returns a soup from a file
    :param path: Path to file
    :return: Soup document
    """
    with open(path, encoding="utf-8") as f:
        return BeautifulSoup(f, "lxml")