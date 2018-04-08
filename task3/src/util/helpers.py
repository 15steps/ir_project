from bs4 import BeautifulSoup, Comment, NavigableString


def getsoup(path):
    """
    Constructs a BeautifulSoup from a HTML file
    """
    with open(path) as fp:
        soup = BeautifulSoup(fp, "lxml")
    return soup


def getinnertext(node):
    """
    Gets all inner text given a node
    Ex: <div class="info"><span>data</span></div>
    returns the string "data"
    """
    if isinstance(node, NavigableString):
        return str(node)
    else:
        return ''.join(node.find_all(text=True))


def preprocess(data):
    """
    Removes all comment tags from the document
    """
    for element in data.find_all(text=lambda text: isinstance(text, Comment)):
        element.extract()
    return data
