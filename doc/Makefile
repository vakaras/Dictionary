default: fresh

doc.pdf: uml.1
	xelatex -no-pdf main.tex
	xelatex -no-pdf main.tex
	xdvipdfmx main.xdv
	mv main.pdf doc.pdf

uml.1:
	mpost uml.mp

fresh: clear doc.pdf

cleanFigs:
	rm -f uml.1

clean: cleanFigs
	rm -f *.aux *.log *.xdv *.out *.toc

clear: clean
	rm -f *.pdf
