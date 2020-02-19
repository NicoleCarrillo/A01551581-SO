compila:
	gcc hello.c -o hello
	./hello

clean: 
	rm hello

push: 
	git add .
	git commit -m "mensaje automatico"
	git push origin master
