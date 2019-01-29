# Traitement d'image numériques
## Université Paris Descartes -  License 3 - 2019

Ce repository est créé avec l'objectif d'aider aux étudiants à créer leur projets et comprendre les commandes nécessaires pour développer des application de traitement d'images.

Le code sera documenté en anglais mais les tutoriaux seront présentés en français.

Les slides présentés pendant les séance de TD sont disponibles sur [Google Drive](https://drive.google.com/open?id=15r5gdZQWGZecuqCbIxyPjIXSFedAjs4Q). 

Comme reference, la documentation du code d'ImageJ et d'ImgLib2 sont disponibles sur [http://javadoc.scijava.org/](http://javadoc.scijava.org/)

En addition, les tutoriaux officiels d'ImageJ sont disponibles sur le repository GitHub [https://github.com/imagej/tutorials](https://github.com/imagej/tutorials)

## Installation du code
### 1 Télécharger le code
Pour télécharger le code de ce repository il suffit d'utiliser un outil de gestion de code Git (ex. SmartGit ou SourceTree). Ici, L'environnement de développement Eclipse est utilisé pour télécharger le code dans le disque dur.

1. Ouvrez Eclipse.
2. Activez la perspective git d'Eclipse

![alt text](https://raw.githubusercontent.com/danyfel80/descartes-image-L3-2019/master/img/screenshot1.png)

![alt text](https://raw.githubusercontent.com/danyfel80/descartes-image-L3-2019/master/img/screenshot2.png)

3. Copiez l'adresse du repository (`https://github.com/danyfel80/descartes-image-L3-2019.git`). Puis, collez cette adresse dans Eclipse en faisant clic droit sur le panel gauche et sur l'option "Paste Repository Path or URI".

![alt text](https://raw.githubusercontent.com/danyfel80/descartes-image-L3-2019/master/img/screenshot3.png)

![alt text](https://raw.githubusercontent.com/danyfel80/descartes-image-L3-2019/master/img/screenshot4.png)

Sélectionnez la location du projet et puis cliquez sur _Finish_.

4. Retournez à la perspective Java

![alt text](https://raw.githubusercontent.com/danyfel80/descartes-image-L3-2019/master/img/screenshot5.png)

### 2 Importer les projets sur Eclipse
Pour importer les projets téléchargés on utilisera Maven sur Eclipse.
1. Sélectionnez le menu File>Import....
2. Dans la section _Maven_ sélectionnez _Existing Maven Projects_.

![alt text](https://raw.githubusercontent.com/danyfel80/descartes-image-L3-2019/master/img/screenshot6.png)

3. Cliquez _Next >_.
4. Sélectionnez la location du projet téléchargé (le dossier **license3.image**) et puis cliquez sur _Finish_.

![alt text](https://raw.githubusercontent.com/danyfel80/descartes-image-L3-2019/master/img/screenshot7.png)

Le projet sera configuré et les dépendances seront téléchargés (ImageJ inclus).

### 3 Ouvrir les plugins développés
Pour ouvrir les plugins développés pour ImageJ, il suffit de lancer la classe `LaunchImageJ` du projet _launchImageJ_.

![alt text](https://raw.githubusercontent.com/danyfel80/descartes-image-L3-2019/master/img/screenshot8.png)

Une fois lancée, ImageJ s'affichera et les plugins seront disponibles sur le menu _Plugins>TD x>Nom du plugin_.

![alt text](https://raw.githubusercontent.com/danyfel80/descartes-image-L3-2019/master/img/screenshot9.png)

## Différentes actions sur le projet
### Pour créer un nouveau sous-projet
Pendant les séances de TD plusieurs projets seront créés. Ici, on explique la création d'un nouveau projet d'ImageJ.
1. Faites clic droit sur le projet **licence3.image**, dans le menu Maven sélectionnez New _Maven Module Project_.

![alt text](https://raw.githubusercontent.com/danyfel80/descartes-image-L3-2019/master/img/screenshot10.png)

2. Donnez un nom à votre projet et cochez "_Create a simple project_" et cliquez _Next_.

![alt text](https://raw.githubusercontent.com/danyfel80/descartes-image-L3-2019/master/img/screenshot11.png)

3. Changez le mode de _Packaging_ à **jar** et cliquez _Finish_.

![alt text](https://raw.githubusercontent.com/danyfel80/descartes-image-L3-2019/master/img/screenshot12.png)

4. Ouvrez le fichier _pom.xml_ et changez-le de façon qu'il contient l'information suivante:

Regardez bien que le nom d'artifact correspond au nom de votre nouveau projet, le reste de balises xml peuvent rester qu'elles sont présentées. Si vous voulez, donnez aussi une description au projet.
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  
  <parent>
    <groupId>fr.parisdescartes</groupId>
    <artifactId>licence3.image</artifactId>
    <version>0.0.1</version>
  </parent>
  
  <artifactId>NewProject</artifactId>
  <groupId>fr.parisdescartes</groupId>
  <version>0.0.1</version>
  
  <name>A new project</name>
	<description>The new project descrition</description>
	<url>https://github.com/[MY-ORG]/[MY-REPO]</url>
	<inceptionYear>2018</inceptionYear>
	<organization>
		<name>[MY-ORGANIZATION-NAME]</name>
		<url>[MY-ORGANIZATION-WEB-SITE]</url>
	</organization>
	<licenses>
		<license>
			<name>CC0 1.0 Universal License</name>
			<url>http://creativecommons.org/publicdomain/zero/1.0/</url>
			<distribution>repo</distribution>
		</license>
	</licenses>

	<developers>
		<developer>
			<id>[MY-GITHUB-ID]</id>
			<name>[MY-FULL-NAME]</name>
			<url>https://imagej.net/User:[MY-IMAGEJ-WIKI-ACCOUNT]</url>
			<roles>
				<!-- see https://imagej.net/Team -->
				<role>founder</role>
				<role>lead</role>
				<role>developer</role>
				<role>debugger</role>
				<role>reviewer</role>
				<role>support</role>
				<role>maintainer</role>
			</roles>
		</developer>
	</developers>
	<contributors>
		<contributor>
			<name>None</name>
		</contributor>
	</contributors>

	<mailingLists>
		<mailingList>
			<name>ImageJ Forum</name>
			<archive>http://forum.imagej.net/</archive>
		</mailingList>
	</mailingLists>

	<scm>
		<connection>scm:git:git://github.com/[MY-ORG]/[MY-REPO]</connection>
		<developerConnection>scm:git:git@github.com:[MY-ORG]/[MY-REPO]</developerConnection>
		<tag>HEAD</tag>
		<url>https://github.com/[MY-ORG]/[MY-REPO]</url>
	</scm>
	<issueManagement>
		<system>GitHub Issues</system>
		<url>http://github.com/[MY-ORG]/[MY-REPO]/issues</url>
	</issueManagement>
	<ciManagement>
		<system>None</system>
	</ciManagement>

	<properties>
		<package-name>[MY-PACKAGE-PREFIX]</package-name>
		<license.licenseName>cc0</license.licenseName>
		<license.copyrightOwners>N/A</license.copyrightOwners>
	</properties>

	<repositories>
		<repository>
			<id>imagej.public</id>
			<url>http://maven.imagej.net/content/groups/public</url>
		</repository>
	</repositories>

	<dependencies>
		<dependency>
			<groupId>net.imagej</groupId>
			<artifactId>imagej</artifactId>
		</dependency>
    <dependency>
      <groupId>net.imglib2</groupId>
			<artifactId>imglib2-algorithm-gpl</artifactId>
    </dependency>
	</dependencies>
</project>
```

5. Créez vos classes à l'intérieur du dossier _src/main/java_.

Vous êtes prêt à coder!

### Inclure une dépendance dans les projets

Pour include une dépendance dans vos projets maven il suffit de modifier le fichier _pom.xml_ du projet de tel façon que le repository de la dépendance est inclus :

```xml
<repositories>
	<repository>
		<id>imagej.public</id>
		<url>http://maven.imagej.net/content/groups/public</url>
	</repository>
	<repository>
		... chaque repository
	</repository>
</repositories>
```

Puis, il faut déclarer la dépendance du projet (_imglib2-algorithm-gpl_ dans l'exemple) :

```xml
<dependencies>
	<dependency>
		<groupId>net.imglib2</groupId>
		<artifactId>imglib2-algorithm-gpl</artifactId>
	</dependency>
	<dependency>
		... chaque dépendance
	</dependency>
</dependencies>
```

Finalement, sauvegardez le fichier. Eclipse détectera le changement du fichier et téléchargera les libraries necessaires dans votre projet.
