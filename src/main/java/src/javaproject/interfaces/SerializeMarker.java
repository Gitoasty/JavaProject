package src.javaproject.interfaces;

import src.javaproject.classes.*;

import java.io.Serializable;

public sealed interface SerializeMarker extends Serializable permits Account, Company, Contract, Project, SerialWriter, Worker {
}
