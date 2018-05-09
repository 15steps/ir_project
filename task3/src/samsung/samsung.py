from requests import request
from bs4 import BeautifulSoup
import re

url = 'https://www.samsung.com/us/mobile/phones/galaxy-s/galaxy-s9-256gb-unlocked-sm-g960uzkfxaa/#specs'


def main():
    pg = request('GET', url).content
    soup = BeautifulSoup(pg, 'lxml')
    specs = soup.find(class_='spec-details__list')

    for li in specs.find_all('li'):
        spec = li.find(class_='specs-item-name').text
        vals = li.find_all('p')
        vals = ' '.join([*map(lambda it: it.text, vals)])
        print('Spec: {0}; Val: {1}'.format(spec, vals))

    return None


if __name__ == '__main__':
    main()
