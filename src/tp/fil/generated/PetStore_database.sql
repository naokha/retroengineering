		CREATE TABLE SellerContactInfo (
						contactInfoID VARCHAR(255)
				
,						lastName VARCHAR(255)

,						firstName VARCHAR(255)

,						email VARCHAR(255)

		);
		CREATE TABLE Tag (
							tagID INTEGER
				
,						tag VARCHAR(255)

,							refCount INTEGER

		);
		CREATE TABLE Address (
						addressID VARCHAR(255)
				
,						street1 VARCHAR(255)

,						street2 VARCHAR(255)

,						city VARCHAR(255)

,						state VARCHAR(255)

,						zip VARCHAR(255)

,								latitude FLOAT

,								longitude FLOAT

,						COMMA VARCHAR(255)

		);
		CREATE TABLE FileUploadResponse (
						itemId VARCHAR(255)
				
,						productId VARCHAR(255)

,						message VARCHAR(255)

,						status VARCHAR(255)

,						duration VARCHAR(255)

,						durationString VARCHAR(255)

,						startDate VARCHAR(255)

,						endDate VARCHAR(255)

,						uploadSize VARCHAR(255)

,						thumbnail VARCHAR(255)

		);
		CREATE TABLE Category (
						categoryID VARCHAR(255)
				
,						name VARCHAR(255)

,						description VARCHAR(255)

,						imageURL VARCHAR(255)

		);
		CREATE TABLE RatingBean (
						itemId VARCHAR(255)
				
,							grade INTEGER

,					cf INTEGER FOREIGN KEY REFERENCES CatalogFacade(catalogFacadeId)	
		);
		CREATE TABLE PayPalBean (
						postData VARCHAR(255)
				
		);
		CREATE TABLE ZipLocation (
							zipCode INTEGER
				
,						city VARCHAR(255)

,						state VARCHAR(255)

		);
		CREATE TABLE Item (
						itemID VARCHAR(255)
				
,						productID VARCHAR(255)

,						name VARCHAR(255)

,						description VARCHAR(255)

,						imageURL VARCHAR(255)

,						imageThumbURL VARCHAR(255)

,							price INTEGER

,					address INTEGER FOREIGN KEY REFERENCES Address(addressId)	
,					contactInfo INTEGER FOREIGN KEY REFERENCES SellerContactInfo(sellerContactInfoId)	
,							totalScore INTEGER

,							numberOfVotes INTEGER

,							disabled INTEGER

		);
		CREATE TABLE Product (
						productID VARCHAR(255)
				
,						categoryID VARCHAR(255)

,						name VARCHAR(255)

,						description VARCHAR(255)

,						imageURL VARCHAR(255)

		);
		CREATE TABLE CatalogFacade (
						emf VARCHAR(255)
				
,						utx VARCHAR(255)

,							bDebug BOOLEAN

		);
