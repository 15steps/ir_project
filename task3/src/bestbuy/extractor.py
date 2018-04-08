from helpers import getsoup, preprocess, getinnertext

PHONE_PAGE = "../../html/bestbuy/phone.html"


def main():
    soup = getsoup(PHONE_PAGE)
    body = soup.body
    normalized = preprocess(body)
    extract(normalized)


def extract(body):
    title = body.h1.string
    model = getinnertext(body.find_all(class_="model-number")[0])
    # print(title)
    # print(model)
    print(body.find_all(class_="specification-group"))


if __name__ == "__main__":
    main()
