package com.hibernate.utility;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ConfigurationUtility {

	// Method to get SessionFactory instance
	public static SessionFactory getsFactory() {

		// Step 1: Creating a StandardServiceRegistry which loads the configuration from hibernate.cfg.xml
		// StandardServiceRegistry is used to configure and bootstrap Hibernate's internal services.
		StandardServiceRegistry ssRegistry = new StandardServiceRegistryBuilder()
				.configure("config.xml")  // Reading the configuration file 'config.xml' which contains Hibernate settings.
				.build();  // Building the registry using the provided configuration.

		// Step 2: Building Metadata instance from the StandardServiceRegistry
		// Metadata is a representation of the mapped entities, configurations, etc., from Hibernate mappings.
		Metadata metadata = new MetadataSources(ssRegistry)  // Initializing MetadataSources with the registry
				.getMetadataBuilder()  // Get the metadata builder to create metadata.
				.build();  // Build the metadata object which holds the mapping of entities and Hibernate settings.

		// Step 3: Using the Metadata instance to build the SessionFactory
		// SessionFactory is a factory class used to open Hibernate sessions which interact with the database.
		// This is the core object responsible for creating sessions.
		SessionFactory sFactory = metadata.buildSessionFactory();  // Creating a SessionFactory object using metadata.

		// Returning the SessionFactory object which will be used to open sessions for database interaction.
		return sFactory;
	}
}
