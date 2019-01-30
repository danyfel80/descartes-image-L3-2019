# Création d'une image avec gradient

Ce projet contient un plugin _Gradient_ qui génère un image d'une taille donnée par l'utilisateur. Les intensités des pixels dans l'image forment un gradient diagonal.

Le gradient est crée à partir de la position de chaque pixel, l'intensité de chaque pixel est affecté avec la somme des coordonnées du pixel. Par exemple, le pixel _(3, 6)_ est affecté avec une intensité _9_ equivalente à la somme de 6 et 3.

D'ailleurs, dans le code, le parcours des pixels dans l'image se fait avec un _curseur_ qui se deplace sur chaque pixel de l'image.

```java
final Cursor<DoubleType> c = gradientImg.localizingCursor();
```

le parcours et fait avec un boucle `while` qui prend en compte l'etat du curseur (`c.hasNext()`).

_**À noter:** Il existent differents types de curseur, ici le curseur utilisé se deplace horizontalement pour chaque ligne de l'image. D'autres exemples de curseur sont [BresenhamLine](http://javadoc.scijava.org/ImgLib2/net/imglib2/algorithm/region/BresenhamLine.html), [RectangleCursor](http://javadoc.scijava.org/ImgLib2/net/imglib2/algorithm/region/localneighborhood/RectangleCursor.html), [RandomAccess](http://javadoc.scijava.org/ImgLib2/net/imglib2/RandomAccess.html)_