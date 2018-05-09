from requests import request
from bs4 import BeautifulSoup
import re


url = 'http://www.mi.com/en/mix2/specs/'


def main():
    pg = request('GET', url).content
    soup = BeautifulSoup(pg, 'lxml')
    specs = soup.find(class_='feature')
    dls = specs.find_all('dl')

    for dl in dls:
        feature = dl.find('dt').text
        value = dl.find('dd').text

        print('{0}: {1}'.format(feature, value))


if __name__ == '__main__':
    main()