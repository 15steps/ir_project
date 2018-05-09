from requests import request
from bs4 import BeautifulSoup
import re


url = 'https://www.htc.com/us/smartphones/htc-u11/#!color=red'


def main():
    pg = request('GET', url).content
    soup = BeautifulSoup(pg, 'lxml')
    specs = soup.find_all(class_=re.compile('column (one|two|three)'))

    for spec in specs:
        lis = spec.find_all('li')
        for li in lis:
            if li.find('h4'):
                print('Spec: {0}'.format(li.find('h4').getText()))
            spans = li.find_all('span')
            spans = ' '.join([*map(lambda it: it.getText(), spans)])
            print('\tValue: {0}'.format(spans))


if __name__ == '__main__':
    main()
