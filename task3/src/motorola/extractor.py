from requests import request
from bs4 import BeautifulSoup
import re


url = 'https://www.motorola.com/us/products/moto-g-gen-6'


def main():
    pg = request('GET', url).content
    soup = BeautifulSoup(pg, 'lxml')
    specs = soup.find_all(class_='specs-summary-wrapper')

    for section in specs:
        title = section.find('h6').text
        value = section.find('p').text
        print('{0}: {1}'.format(title, value))


if __name__ == '__main__':
    main()


