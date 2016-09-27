# LO23TD01
Repo principal pour le projet LO23 2016. Le code de production et les dernières branches de code se trouvent ici

## Guideline pour l'utilisation de git
Vous devez avant tout avoir une compréhention au moins basique des concepts suivant:
- branche
- repo
- git rebase
- git merge

Si vous avez besoin d'information sur l'utilisation de git, consultez la cheat sheet git disponible sur le repo, elle contient des informations pour débutant et c'est tout ce dont vous a aurez besoin

## Guideline sur le fonctionnement du projet
Convention: Il est *INTERDIT* de push des commit sur les branches release et develop. Ce droit est réservé au resp. de chaque groupe Vous devrez effectuer des pull request c'est à dire demander au resp de rapatrier vos modifications sur la branche de développement. Pour qu'une modification soit accepté elle devrait répondre aux conventions suivante:

### Branche
Une branche de dev devra être nommé selon la convention suivante: develop/XXX/YYYYYY avec:
- GGGG: votre groupe parmis les 4
- XXX: un trigramme unique identifiant l'auteur de la branche composé idéalement (sauf doublon) de la première lettre de votre prénom suivi de la première lettre de votre nom de famille et enfin de la dernière lettre de votre nom de famille. Par exemple pour *Jean Dupont* le trigramme est *JDT*
- YYYYYY: le nom du ticket trello correspondant à la tâche réalisé dans les commit. il est IMPERATIF de créer une tâche trello pour chaque branche de dev même si ce dernier n'existe pas à la base. S'il s'agit vraiment d'un bugfix très court on peut accépter bugfix

Il existe une branche "release" globale et 4 branches develop, une par groupe. Les branches develop sont sous la responsabilité des resp de chaque groupe. Il est fortement recommandé d'effectuer des revues de code à chaque intégration sur les branches develop. L'intégration sur la branche release se fera SANS revu de code, j'insiste sur le fait qu'il est impératif de les effectuer avant intégration dans les branches develop

### Commit
Un commit se doit d'être complet, lisible et traçable. Par cela on entend:
- le commit doit aboutir à une version qui compile. Un commit qui ne compile pas devra être squashé avec le commit suivant et ainsi de suite jusqu'à la prochaine version qui compile (git rebase -i sur develop qui compilera toujours)
- le commit doit avoir un message clair décrivant TOUT ce qui est fait dans ce commit. Les messages "bugfix" et "wip" ne constituent pas des descriptions clair de ce qui a été fait.
- le tout début du message du commit est réservé pour faire référence au ticket trello correspondant à la fonctionnalité implémenté dans le commit. Exemple : "module dés: création du RNG pour les rotations du dés, bugfix sur l'antialiasing des faces des dés"

### Integration
- Une branche prête à être intégré devra être préalablement rebasé sur la dernière version de la branche develop de votre groupe
L'intégration sur la branche release se fera régulièrement. Un rebase toute les 3 ou 4 features (branches de dev) est recommandé mais chaque groupe est libre de son mode de travail
- La compatibilité d'une branche de dev avec la branche develop du groupe est à la responsabilité du programmeur travaillant sur cette branche
- La compatibilité de la branche develop avec la branche release est à la responsabilité du responsable de la branche
- Comme sous entendu plus haut les intégrations aux branches protégé se font par rebase de la branche de dev sur le dernier commit de de la branche protégé. 
