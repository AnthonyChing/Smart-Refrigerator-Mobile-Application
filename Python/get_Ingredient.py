def get_ingredient():
    text = []
    while True:
        ingredient = input('Input ingredient. If finished, type " end ": ')
        if ingredient == "end":
            break
        text.append(ingredient)
    return text
# don't need this anymore
