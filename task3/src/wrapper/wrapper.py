from bs4 import BeautifulSoup, Tag
import requests
import re
from time import time
from dicttoxml import dicttoxml
from xml.dom.minidom import parseString

samsung = 'https://www.samsung.com/us/mobile/phones/galaxy-note/' \
          'galaxy-note8-64gb-unlocked-deepsea-blue-sm-n950uzbaxaa/'  # working well
lg = 'http://www.lg.com/us/mobile-phones/g6'  # didnt quite work
apple = 'https://www.apple.com/iphone-8/specs/'  # working very well
sony = 'https://www.sonymobile.com/us/products/phones/xperia-xz2/specifications/'
xiaomi = 'http://www.mi.com/en/mi6/specs/'
asus = 'https://store.asus.com/us/item/201701AM240000082/A26208-ASUS-ZenFone-3-ZE552KL-5.5'
moto = 'https://www.motorola.com/us/products/moto-g-gen-6'
blu = 'http://bluproducts.com/devices/vivo-xl3-plus/'
htc = 'https://www.htc.com/us/smartphones/htc-u11/#!color=red'
banggood = 'https://www.banggood.com/Bluboo-S3-6_0-Inch-Sharp-FHD-8500mAh-12V2A-NFC-' \
           '4GB-RAM-64GB-ROM-MTK6750T-1_5GHz-4G-Smartphone-p-1283089.html?rmmds=category&cur_warehouse=HK'
huawei = 'https://consumer.huawei.com/us/phones/mate-se/specs/'
oppo = 'https://www.oppo.com/en/smartphone-a83_2018#section-product-specs'


def main():
    sites = {
        "samsung": samsung,
        "lg": lg,
        "apple": apple,
        "sony": sony,
        "xiaomi": xiaomi,
        "asus": asus,
        "moto": moto,
        "blu": blu,
        "htc": htc,
        "banggood": banggood,
        "huawei": huawei,  # Extra
        "oppo": oppo  # Extra
    }

    regexes = {
        'screen_size': r'(\d\.\d+[\”\"])|(\d\.\d+-inch)|(\d\.\d+[ ]Inch)',
        'camera_res': r'(\d\d|\d\d\.\d+|\d\d)[ \-]?(MP|Megapixel)\s',
        'screen_resolution': r'\d{3,4}[ ]?[x*\-by]+[ ]?\d{3,4}',
        'battery_capacity': r'\d{1,2}[,]?\d{3}[ ]?mAh',
        'ram': r'\s\d[ ]?GB',
        'internal_memory': r'\d{2,3}[ ]?GB',
        'processor_speed': r'\d\.\d{1,2}[ ]?(Ghz|GHz)',
        'weight': r'\d?\.?\d{2,3}[ ]?(oz|g)'
    }

    _t0 = time()
    acc_time = []

    for site_name, url in sites.items():
        t0 = time()
        print(site_name)
        specs = specsection(url, verbose=(True if site_name == '' else False))
        pagedict = {}

        for spec_name, rx in regexes.items():
            # print('{0}: {1}'.format(spec_name, getspec(specs, rx)))
            pagedict[spec_name] = getspec(specs, rx)

        xml = dicttoxml(pagedict, attr_type=None, custom_root="page")
        print(parseString(xml).toprettyxml())
        break

        print('*' * 50)
        acc_time.append(time() - t0)

    print('Total time: {0:.3f}s'.format(time() - _t0))
    print('Average extraction time: {0: .3f}s'.format(sum(acc_time)/len(acc_time)))


def getspec(txt: str, rx):
    compre = re.compile(rx, re.M)
    res = compre.search(txt)
    return None if not res else res.group()


def specsection(url: str, verbose=False):
    """
    Attempts to get the spec section
    of a smartphone page
    :return: The spec section/div
    """
    page = requests.get(url)
    bsoup = BeautifulSoup(page.text, 'lxml')

    # return bsoup.find('body').getText()

    specs = bsoup.find_all(find_specs)
    specs = ' '.join([*map(lambda tag: tag.getText(), specs)])

    verbose and print(specs)

    return re.sub(r'[\s]', ' ', specs)


def find_specs(tag: Tag):
    rx = re.compile(r'.*(spec|feature).*')
    for attr, val in tag.attrs.items():
        if rx.search(str(val)):
            return True
    return False


if __name__ == '__main__':
    main()
