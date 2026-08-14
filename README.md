# Système de Réservation d'Hôtels — Architecture GRPC

## Guide d'utilisation

## 1. Prérequis

- Système d'exploitation : Linux recommandé (Windows possible)
- Java : JDK 17 ou supérieur
- Maven : pour la compilation et la gestion des dépendances
- gRPC & Protocol Buffers : utilisés pour la communication entre les agences et les hôtels

## 2. Contenu du projet

Le projet est composé des modules suivants :

- `hotel1` : serveur gRPC représentant l'hôtel Paradis
- `hotel2` : serveur gRPC représentant l'hôtel Champs
- `agence` : client gRPC avec une interface graphique Swing

## 3. Lancement du projet

Ouvrir IntelliJ IDEA et importer les trois projets :

- `hotel1`
- `hotel2`
- `agence`

### Lancer les serveurs gRPC des hôtels

**Hôtel Paradis (Paris)**

- Serveur gRPC démarré sur le port `9091`
- Services exposés :
  - consultation des chambres disponibles
  - réservation des chambres
  - annulation des réservations

**Hôtel Champs (Paris)**

- Serveur gRPC démarré sur le port `9092`
- Services exposés :
  - consultation des chambres disponibles
  - réservation des chambres
  - annulation des réservations

### Lancer l'agence

Lancer ensuite le projet `agence` afin d'ouvrir l'interface graphique Swing.

## 4. Utilisation de l'application

### Connexion à l'agence

Au lancement de l'application, une interface de connexion s'affiche. Deux agences sont actuellement disponibles :

| Agence | Email | Mot de passe |
|---|---|---|
| Agence 1 | agence1@gmail.com | agence1 |
| Agence 2 | agence2@gmail.com | agence2 |

*(Ces identifiants sont enregistrés dans la base de données.)*

### Hôtels disponibles (Paris uniquement)

- Hôtel Paradis – Paris
- Hôtel Champs – Paris

### Réductions appliquées selon l'agence

| Agence | Hôtel Paradis | Hôtel Champs |
|---|---|---|
| Agence 1 | 15% | 10% |
| Agence 2 | aucune convention (aucune chambre disponible) | 20% |

## 5. Aspects techniques

- La communication entre les agences et les hôtels est assurée exclusivement par gRPC.
- Les messages et services sont définis à l'aide de Protocol Buffers (`.proto`).

## Auteur

Sabrina MOUFOK
