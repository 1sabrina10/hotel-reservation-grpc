# Système de Réservation d'Hôtels — Architecture gRPC

## Guide d'utilisation

## 1. Prérequis

- Système : Linux recommandé (Windows possible mais non garanti)
- Java : JDK 17
- Maven : pour compiler et gérer les dépendances (plugin `protobuf-maven-plugin` pour la génération du code gRPC)
- IDE recommandé : IntelliJ IDEA

## 2. Contenu du projet

Le dépôt contient plusieurs projets indépendants :

- `/hotel1` → Serveur gRPC Hôtel 1
- `/hotel2` → Serveur gRPC Hôtel 2
- `/agence` → Client gRPC (interface Swing)
- `README.md`
- Rapport du projet

## 3. Lancer le projet

### a. Importer les projets

Ouvrir IntelliJ et importer les projets `hotel1`, `hotel2` et `agence`.

### b. Lancer les serveurs d'hôtels

- Hôtel 1 → port `9093`
- Hôtel 2 → port `9094`

Les services (consultation et réservation) sont exposés via gRPC sur ces ports, définis dans les fichiers `.proto` du projet.

### c. Lancer l'Agence

Lancer le projet `agence` pour ouvrir l'interface graphique. Le client se connecte aux serveurs hôtels via les stubs gRPC générés à partir des fichiers `.proto`.

## Notes

### Connexion

Une fenêtre de connexion apparaît. Actuellement, deux agences sont disponibles :

| Agence | Login | Mot de passe |
|---|---|---|
| Agence 1 | agence1 | 123 |
| Agence 2 | agence2 | 10 |

*(Ces identifiants existent en base de données.)*

### Hôtels disponibles

- Parisien
- Lyonnais

### Réductions appliquées selon l'agence

| Agence | Parisien | Lyonnais |
|---|---|---|
| Agence 1 | 10% | 15% |
| Agence 2 | pas de convention (pas de chambre dispo) | 20% |

### Recherche de chambres

- Sélectionner la ville, la date début, la date fin, et le nombre de personnes (1 ou 2).
- Le système empêche la réservation de la même chambre pour des dates déjà occupées.
- Les agences et hôtels communiquent via services gRPC (protocole HTTP/2, sérialisation Protocol Buffers).
- Les données (agences, hôtels, chambres, clients, réservations) sont stockées en base de données.

## Auteur

Sabrina MOUFOK
