#!/bin/sh

for svg in */*.svg; do

	name=`echo $svg | sed -e 's/\.svg//'`
	png=$name.png
	jpg=$name.jpg

	echo $name

	inkscape -e $png $svg
	gm convert $png $jpg

done
