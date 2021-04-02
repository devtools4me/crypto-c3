# Anaconda

```
https://www.anaconda.com/products/individual#macos
```

# Jupyter

# Install

## Homebrew

```
brew install anaconda
vi ~/.zshrc
chmod ~/.zshrc
ls -lo ~/.zshrc
chmod 777 ~/.zshrc
ls /usr/local/anaconda3/bin
export PATH="/usr/local/anaconda3/bin:$PATH"
mkdir jupyter
cd jupyter/
jupyter notebook
```

## Pip3

```
python3
exit
pip3 install jupyter --user
export PATH="/Users/sergei/Library/Python/3.8/bin:$PATH"
```