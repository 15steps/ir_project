from dicttoxml import dicttoxml
from pathlib import Path
from helpers import getsoup
from bs4 import BeautifulSoup, Tag, Comment
import re
from xml.dom.minidom import parseString


sony = 'https://www.sonymobile.com/us/products/phones/xperia-xz2/specifications/'


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


def main():
    pagedict = {
        "title": "",
        'screen_size': "",
        'camera_res': "",
        'screen_resolution': "",
        'battery_capacity': "",
        'ram': "",
        'internal_memory': "",
        'processor_speed': "",
        'weight': "",
        "body": ""
    }

    n = 0
    p = Path('files_new/')
    for folder in p.iterdir():
        print(f"Converting folder: {folder.name}...")
        if folder.is_dir():
            for file in folder.iterdir():
                if file.is_file() and file.name.endswith(".html"):
                    soup = getsoup(file)
                    specs = specsection(soup)

                    for spec_name, rx in regexes.items():
                        pagedict[spec_name] = getspec(specs, rx)
                    pagedict["title"] = getsouptitle(soup)
                    pagedict["body"] = getbodytext(soup)

                    qtd_specs = [*filter(lambda val: val, pagedict.values())]
                    if len(qtd_specs) < 8:
                        print(f"skiped {file.name}")
                        continue

                    xml = dicttoxml(pagedict, attr_type=None, custom_root="page")
                    prettyxml = parseString(xml).toprettyxml()
                    print(f"Writing file: {file.name}")
                    write_to_file(prettyxml, f'{n}.xml')
                    n += 1
        print(f"Done with {folder.name}!")
    print("Finished")


def getbodytext(soup: BeautifulSoup):
    texts = soup.findAll(text=True)
    visible_text = filter(visible_tag, texts)
    return u" ".join(t.strip() for t in visible_text)


def visible_tag(element):
    if element.parent.name in ['style', 'script', 'head', 'title', 'meta', '[document]']:
        return False
    if isinstance(element, Comment):
        return False
    return True


def getsouptitle(soup: BeautifulSoup):
    return soup.find("title").text


def write_to_file(text, filename):
    filename = filename.replace(".html", ".xml")
    with open(f"xml/{filename}", "w", encoding="utf-8") as writer:
        writer.write(text)
        writer.close()


def getspec(txt: str, rx):
    compre = re.compile(rx, re.M)
    res = compre.search(txt)
    return None if not res else res.group()


def specsection(bsoup: BeautifulSoup, verbose=False):
    """
    Attempts to get the spec section
    of a smartphone page
    :return: The spec section/div
    """
    # page = requests.get(url)
    # bsoup = BeautifulSoup(page.text, 'lxml')

    # return bsoup.find('body').getText()

    specs = bsoup.find_all(find_specs)
    specs = ' '.join([*map(lambda tag: tag.getText(), specs)])

    verbose and print(specs)
    return re.sub(r'[\s]', ' ', specs).strip()


def find_specs(tag: Tag):
    rx = re.compile(r'.*(spec|specs|feature).*')
    for attr, val in tag.attrs.items():
        if rx.search(str(val)):
            return True
    return False


if __name__ == '__main__':
    main()