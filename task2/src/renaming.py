from pathlib import Path
import os
import re
import random

def main():
    # os.chdir(Path('../../task1/files_new'))
    # os.chdir(Path('./'))
    # print(os)

    # print(os.listdir(os.getcwd()))
    for folder in getpaths():
        for file in folder:
            fullpath = os.path.abspath(os.path.dirname(__file__))
            folderpath = os.path.join(fullpath, file.parent)
            # folderpath = re.sub('\s', '\ ', folderpath)
            folderpath = re.sub(r'/task2/src/\.\./\.\.', '', folderpath)
            os.chdir(folderpath)
            os.rename(file.name, str(random.randint(100, 1000000)) + '.html')



def getpaths(root='../../task1/files_new') -> [[Path]]:
    path = Path('../../task1/files_new')
    fs = []
    for folder in path.iterdir():
        site = folder.name
        if site != 'robots':
            p = str(folder) + '/'
            files = Path(p)
            fs.append(list(files.iterdir()))
    return fs

if __name__ == '__main__':
    main()