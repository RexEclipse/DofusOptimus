:: Placement dans le bon repertoire
:: ⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠
:: ⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠
:: Il faut modifier le "Path" juste en dessous afin de correspondre à l'emplacement des fichiers 
:: ⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠
:: ⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠
call cd C:\Users\"Pseudo"\Desktop\VLDofusBot

:: Verification si du code plus à jour est disponible
call git pull


:: Construction du jar executable 
:: La commande s'éxecutera toujours, mais gradle ne recommencera pas une construction entiere si il n'y a pas de code plus à jour trouvé sur l'étape précédente
call gradlew build


:: Lancement du jar executable 
:: (Pour le moment ca fonctionne, mais quand Vldb decidera de changer de version, il faudra modifier la ligne en dessous)
call java -jar .\VLDofusBotApp\build\libs\VLDofusBotApp-1.4.0.jar
