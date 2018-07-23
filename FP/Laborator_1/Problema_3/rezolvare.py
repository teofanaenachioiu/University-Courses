a=int(input("a="))
b=int(input("b="))
if a*b==0:
    print(a*b)
else:
    while a!=b:
        if a>b:
            a=a-b
        else:
            b=b-a
print("Cel mai mare divizor comun: "+str(a))
