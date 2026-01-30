/* LivreHero est le “chef d’orchestre” du livre.

Elle sert à :

lire un livre LDVEH depuis un fichier texte

créer tous les paragraphes

détecter les choix (liens)

détecter les objets récupérables

permettre de jouer / parcourir le livre

gérer l’inventaire du joueur

C’est le lien entre le fichier texte et le jeu/graphe.


🔹 loadFromFile(String filePath) ⭐

👉 La plus importante

Elle fait ceci :

ouvre le fichier texte

lit ligne par ligne

quand elle voit un numéro → nouveau paragraphe

quand elle voit “rendez-vous au X” → crée un choix

quand elle voit “Vous trouvez un …” → crée un objet

range tout au bon endroit

💡 Après ça, le livre est entièrement chargé en mémoire.


🔹 jouerParagraphe(int numero)

👉 Sert à lire un paragraphe comme dans un jeu

affiche le texte

récupère les objets du paragraphe

les met dans l’inventaire

➡️ C’est une première version de gameplay.


🔹 getParagraphe(int numero)

👉 Permet d’aller chercher une page précise du livre.

🔹 getParagraphes()

👉 Permet d’avoir tout le livre
(utilisé pour le graphe et les analyses)    
 
 
 */