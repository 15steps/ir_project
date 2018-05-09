from helpers import getsoup, preprocess, getinnertext
from requests import request
from bs4 import BeautifulSoup


spces_page = "https://www.banggood.com/Xiaomi-Mi-MIX-2-Global-Bands-5_99-inch-6GB-RAM-64GB-ROM-Snapdragon-" \
             "835-Octa-core-4G-Smartphone-p-1198702.html?rmmds=category&ID=531765&cur_warehouse=HK"


"""
Extracts data from the specs section any smartphone page
"""


def main():
    page = request('GET', spces_page).content
    soup = BeautifulSoup(page, 'lxml')
    body = soup.body
    spec_sec = body.find(id='specification')
    tds = spec_sec.find_all('td')
    for td in tds:
        print(td.getText())


def extract(body):
    title = body.h1.string
    model = getinnertext(body.find_all(class_="model-number")[0])
    # print(title)
    # print(model)
    print(body.find_all(class_="specification-group"))


if __name__ == "__main__":
    main()
