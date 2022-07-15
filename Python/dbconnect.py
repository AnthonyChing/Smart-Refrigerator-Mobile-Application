import pyodbc
conn = pyodbc.connect(r'Driver={Microsoft Access Driver (*.mdb, *.accdb)};'
                      r'DBQ=C:\Users\achin\Documents\Database\test.accdb'
                      )
cursor = conn.cursor()
