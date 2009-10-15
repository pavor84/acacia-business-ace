%.bdf: %.txt
	bdfassemble $< > o
	mv o $@
#	x less $@

%.gif: %.bdf
	[ -r $< ]
	PADDING=4 bdftogif $< | giftrans -c $@ > o
	mv o $@
	xv $@ &

%.txt: %.txt.gz
	zcat $< > o
	mv o $@

%.bumap: %.txt
	sed 's/=/0x/;s/U./0x/;s/	[A-Z].*//' $< | \
	makeumap -b $< `basename $@ .bumap`
