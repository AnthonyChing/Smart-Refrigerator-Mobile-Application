import dbconnect
cursor = dbconnect.cursor


def insert(in_type):
    if in_type == "Ingredient":
        print("Insert ingredient name: ")
        ingredient = input()
        cursor.execute(
            '''
            insert into Ingredients (Ingredient_name)
            values (?);
        ''', ingredient
        )
    elif in_type == "Recipe":
        ingredient_list = []
        dish = input("Insert recipe name: ")
        amount = int(input("How many ingredients? "))
        for i in range(amount):
            ingredient_list.append(input("Input ingredient: "))
        difficulty = input("Insert difficulty: ")
        cursor.execute(
            '''
            insert into Recipe (Dish_name, Ingredients, Difficulty)
            values (?,?,?);
        ''', dish, difficulty
        )


def delete(in_type):
    if in_type == "Ingredient":
        print("Delete ingredient: ")
        remove = input()
        cursor.execute(
            '''
            delete from Ingredients where Ingredient_name = ?;    
        ''', remove
        )
    elif in_type == "Dish":
        print("Delete Dish: ")
        remove = input()
        cursor.execute(
            '''
            delete from Recipe 
            where Dish_name='?';    
        ''', remove
        )


def list_(in_type):
    if in_type == "ingredients":
        cursor.execute("select Ingredient_name from Ingredients")
        ans = cursor.fetchall()
        for row in ans:
            print(row[0])
    elif in_type == "recipes":
        cursor.execute("select Dish_name from Recipe")
        ans = cursor.fetchall()
        for row in ans:
            print(row[0])
