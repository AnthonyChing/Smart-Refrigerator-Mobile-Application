import admin
import user
import dbconnect


while True:
    command = input("/")
    # Kill command
    if command == "stop":
        dbconnect.conn.commit()
        break
    # Admin commands
    elif command == "add" or command == "Add":
        in_type = input("Add (i)ngredient or (r)ecipe?")
        if in_type == "ingredient" or in_type == "i":
            admin.insert("Ingredient")
        elif in_type == "recipe" or in_type == "r":
            admin.insert("Recipe")
    elif command == "delete" or command == "Delete":
        in_type = input("Delete (i)ngredient or (d)ish?")
        if in_type == "ingredient" or in_type == "i":
            admin.delete("Ingredient")
        elif in_type == "dish" or in_type == "d":
            admin.delete("Dish")
    elif command == "list" or command == "List":
        in_type = input("List (i)ngredients or (r)ecipes?")
        if in_type == "ingredient" or in_type == "i":
            admin.list_("ingredients")
        elif in_type == "recipes" or in_type == "r":
            admin.list_("recipes")
    # User commands
    elif command == "search":
        user.search()

