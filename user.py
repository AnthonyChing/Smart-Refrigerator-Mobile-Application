import dbconnect
import time
cursor = dbconnect.cursor


def get_text(in_type):
    text = []
    while True:
        if in_type == "ingredient":
            temp = input('Input ingredients. If finished, type "0": ').strip()
            if temp != "":
                if temp == "0":
                    break
                text.append(temp)
                print(temp)
        elif in_type == "name":
            text = input('Input a keyword: ')
            break
    return text


def query_ing(text):
    print("Ingredients: ")
    cou = 1
    for i in text:
        print(str(cou) + "." + i)
        cou += 1
    # Transform Input into numbers
    number_list = []
    if len(text) == 0:
        print("Nothing inserted, please try again.")
    else:
        for i in text:
            cursor.execute(   # executemany
                '''
                select Ingredient_ID
                from Ingredients
                where Ingredient_name = ?;
            ''', i
            )
            ans = cursor.fetchone()
            null = None
            if ans is None:
                print(str(i) + " is not found")
            elif len(ans) != 0:
                number_list.append(ans[0])  # returned (number, )
        # Generate query text
        number_list.sort()
        # query_text = "%".join(list(map(str, number_list)))
        query_text = ""
        for i in number_list:
            query_text = ",".join(list(map(str, number_list)))
        if query_text == "":
            print("No ingredients recognized, please try again.")
        else:
            test = [1, ",", 4]
            for i in test:
                print(i)
            # print(query_text)
            # print(len(number_list))
            cursor.execute(
                '''
                select dish_name
                from RECIPE
                WHERE
                 RECIPE_ID IN
                (SELECT R.RECIPE_ID
                FROM
                 RECIPE R, RI
                WHERE
                 R.RECIPE_ID = RI.RECIPE_ID AND
                 INGREDIENT_ID IN (?)
                GROUP BY R.RECIPE_ID
                HAVING COUNT(*) = ?
                )
            ''', query_text, len(number_list)
            )
            ans = cursor.fetchall()
            # print(ans)
            if len(ans) == 0:
                print("No recipes found.")
            for row in ans:
                print(row)  # returned('recipe', )


def food(test):
    for i in test:
        print(i)
        return i


def feed(number_list):
    if len(number_list) == 1:
        return number_list[0]
    else:
        food = number_list[0]
        del number_list[0]
        feed(number_list)
        return food


def query_rec(text):
    query_text = "%" + text + "%"
    print("Keyword: " + text)
    cursor.execute(
        '''
        select Dish_Name
        from Recipe
        where Dish_Name like (?);
    ''', query_text
    )
    ans = cursor.fetchall()
    if len(ans) == 0:
        print("Nothing found.")
    for row in ans:
        print(row[0])  # returned('recipe', )


def search():
    searching = input("Search recipe with (n)ame/(i)ngredients? ")
    if searching == "name" or searching == "n":
        query_rec(get_text("name"))
    elif searching == "ingredient" or searching == "i":
        query_ing(get_text("ingredient"))
    else:
        print("Unrecognized searching type, please try another one")
