-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : mariadb:3306
-- Généré le : jeu. 19 jan. 2023 à 21:49
-- Version du serveur : 10.10.2-MariaDB-1:10.10.2+maria~ubu2204
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `projet_cgl`
--

-- --------------------------------------------------------

--
-- Structure de la table `affaire`
--

CREATE TABLE `affaire` (
  `ID` int(11) NOT NULL,
  `APPORTEUR_ID` int(11) DEFAULT NULL,
  `DATE` date NOT NULL DEFAULT current_timestamp(),
  `COMMISSION_GLOBALE` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Structure de la table `apporteur`
--

CREATE TABLE `apporteur` (
  `ID` int(11) NOT NULL,
  `PRENOM` varchar(255) NOT NULL,
  `NOM` varchar(255) NOT NULL,
  `PARRAIN_ID` int(11) DEFAULT NULL,
  `IS_DELETED` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB AVG_ROW_LENGTH=1365 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Structure de la table `commission`
--

CREATE TABLE `commission` (
  `AFFAIRE_ID` int(11) NOT NULL,
  `APPORTEUR_ID` int(11) NOT NULL,
  `MONTANT` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Structure de la table `parametres`
--

CREATE TABLE `parametres` (
  `ID` int(11) NOT NULL,
  `CODE` varchar(30) NOT NULL,
  `LABEL` varchar(255) NOT NULL,
  `VALEUR` varchar(255) NOT NULL
) ENGINE=MyISAM AVG_ROW_LENGTH=86 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `parametres`
--

INSERT INTO `parametres` (`ID`, `CODE`, `LABEL`, `VALEUR`) VALUES
(1, 'NB_PARRAINGE_MAX', 'Niveau maximum de parrainage', '5'),
(2, 'NB_MIN_AFFAIRES', 'Nombre d\'affaires minimum à apporter pour rester affilié', '1'),
(3, 'DUREE_MIN_AFFILIE', 'Durée pour apporter une nouvelle affaire afin de rester affilié (en mois)', '3'),
(4, 'DIR_PARR_VALUE', 'Pourcentage de commission touché par un parrain direct', '0.05'),
(5, 'INDIR_PARR_VALUE', 'Pourcentage de commission touché par un parrain indirect', '0.5');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `affaire`
--
ALTER TABLE `affaire`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `affaire_ibfk_1` (`APPORTEUR_ID`);

--
-- Index pour la table `apporteur`
--
ALTER TABLE `apporteur`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_PARRAIN` (`PARRAIN_ID`);

--
-- Index pour la table `commission`
--
ALTER TABLE `commission`
  ADD PRIMARY KEY (`AFFAIRE_ID`,`APPORTEUR_ID`),
  ADD KEY `ID_AFFAIRE` (`AFFAIRE_ID`,`APPORTEUR_ID`),
  ADD KEY `commission_ibfk_1` (`APPORTEUR_ID`);

--
-- Index pour la table `parametres`
--
ALTER TABLE `parametres`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `affaire`
--
ALTER TABLE `affaire`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `apporteur`
--
ALTER TABLE `apporteur`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT pour la table `parametres`
--
ALTER TABLE `parametres`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `affaire`
--
ALTER TABLE `affaire`
  ADD CONSTRAINT `affaire_ibfk_1` FOREIGN KEY (`APPORTEUR_ID`) REFERENCES `apporteur` (`ID`) ON DELETE SET NULL;

--
-- Contraintes pour la table `apporteur`
--
ALTER TABLE `apporteur`
  ADD CONSTRAINT `apporteur_ibfk_1` FOREIGN KEY (`PARRAIN_ID`) REFERENCES `apporteur` (`ID`) ON DELETE SET NULL;

--
-- Contraintes pour la table `commission`
--
ALTER TABLE `commission`
  ADD CONSTRAINT `commission_ibfk_1` FOREIGN KEY (`APPORTEUR_ID`) REFERENCES `apporteur` (`ID`),
  ADD CONSTRAINT `commission_ibfk_2` FOREIGN KEY (`AFFAIRE_ID`) REFERENCES `affaire` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
