from bs4 import BeautifulSoup
import requests
import re


samsung = 'https://www.samsung.com/us/mobile/phones/galaxy-note/' \
          'galaxy-note8-64gb-unlocked-deepsea-blue-sm-n950uzbaxaa/'  # working well
lg = 'http://www.lg.com/us/mobile-phones/g6'  # didnt quite work
apple = 'https://www.apple.com/iphone-8/specs/'  # working very well
sony = 'https://www.sonymobile.com/us/products/phones/xperia-xz2/specifications/'
xiaomi = 'http://www.mi.com/en/mi6/specs/'


def main():
    spec = specsection(sony)
    # print(spec.text)
    screen = getscreensize(spec.text)
    camera = getcamerares(spec.text)
    print(screen)


def getscreensize(txt: str) -> [str]:
    screenrx = re.compile(r'\d[.]\d(\"|-inch)', re.I | re.M)
    return screenrx.findall(txt)


def getcamerares(txt: str) -> [str]:
    mprx = re.compile(r'\d\dMP', re.I | re.M)
    return mprx.findall(txt)


def specsection(url: str):
    """
    Attempts to get the spec section
    of a smartphone page
    :return: The spec section/div
    """
    page = requests.get(url)
    bsoup = BeautifulSoup(page.text, 'lxml')
    rx = r'[\w\W]*(spec)[\w\W]*'
    specs = bsoup.find_all(class_=re.compile(rx))
    n = -1
    for spec in specs:
        length = len([*spec.children])
        if length > n:
            n = length
            thespec = spec
    return thespec


if __name__ == '__main__':
    main()
