# Sukompiliuoti programą.
.PHONY: src
src:
	$(MAKE) -C src

# Sugeneruoti dokumentaciją.
.PHONY: doc
doc:
	$(MAKE) -C doc

# Išvalyti visus generavimo metu sukurtus laikinuosius failus.
.PHONY: clean
clean:
	$(MAKE) -C doc clean

# Palikti tik išeities tekstus.
.PHONY: clear
clear: clean
	$(MAKE) -C doc clear


