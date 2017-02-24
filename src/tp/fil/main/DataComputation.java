package tp.fil.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmt.modisco.java.BodyDeclaration;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.NamedElement;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.gmt.modisco.java.TypeAccess;
import org.eclipse.gmt.modisco.java.VariableDeclarationFragment;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;

public class DataComputation {

	private static Resource dataMetamodel;

	public static void main(String[] args) {
		try {
			Resource javaModel;
			Resource dataModel;
			ResourceSet resSet = new ResourceSetImpl();
			resSet.getResourceFactoryRegistry().
			getExtensionToFactoryMap().
			put("ecore", new EcoreResourceFactoryImpl());
			resSet.getResourceFactoryRegistry().
			getExtensionToFactoryMap().
			put("xmi", new XMIResourceFactoryImpl());

			JavaPackage.eINSTANCE.eClass();

			dataMetamodel = resSet.createResource(URI.createFileURI("src/tp/fil/resources/Data.ecore"));
			dataMetamodel.load(null);
			EPackage.Registry.INSTANCE.put("http://data", 
					dataMetamodel.getContents().get(0));

			javaModel = resSet.createResource(URI.createFileURI("../PetStore/PetStore_java.xmi"));
			javaModel.load(null);

			dataModel = resSet.createResource(URI.createFileURI("../PetStore/PetStore_data.xmi"));

			/*
			 * Beginning of the part to be completed...
			 */
			TreeIterator<EObject> iterator = javaModel.getAllContents();
			ArrayList<EObject> classes = new ArrayList<EObject>(); // save classes here
			while(iterator.hasNext()) {
				EObject currentElem = iterator.next();
				if(getName(currentElem).equals("ClassDeclaration")) {
					ClassDeclaration currentClass = (ClassDeclaration) currentElem;
					Package classPackage = currentClass.getPackage();
					if(classPackage != null && classPackage.getName().equals("model") && getAttribute(classPackage.getPackage(), "name").equals("petstore")) {
						String className = (String) getAttribute(currentClass, "name");
						Iterator<BodyDeclaration> bodyDeclarationIte = ((EList<BodyDeclaration>) getAttribute(currentClass, "bodyDeclarations")).iterator();
						ArrayList<EObject> attributes = new ArrayList<EObject>(); // save attributes here
						while(bodyDeclarationIte.hasNext()) {
							BodyDeclaration currentBodyDeclaration = bodyDeclarationIte.next();
							if(getName(currentBodyDeclaration).equals("FieldDeclaration")) {
								EList<VariableDeclarationFragment> fragments = (EList<VariableDeclarationFragment>) getAttribute(currentBodyDeclaration, "fragments");
								VariableDeclarationFragment currentFragment = fragments.get(0);
								TypeAccess type = (TypeAccess) getAttribute(currentBodyDeclaration, "type");
								NamedElement attributeType = (NamedElement) getAttribute(type, "type");
								String attributeFullTypeName = (String) getAttribute(attributeType, "name");
								String attributeTypeName;
								Boolean isTypeOfAttributeCollection;
								Boolean doesTypeReferenceModelClass;
								//attributes.add(createAttribute(getAttribute(currentFragment, "name"), getAttribute(typeType, "name")));
							}
						}
						classes.add(createClass(className, attributes)); // add class with attributes				
					}
				}
			}
			dataModel.getContents().add(createModel(classes)); // generate model with all classes and their attributes
			/*
			 * End of the part to be completed...
			 */

			dataModel.save(null);

			javaModel.unload();
			dataModel.unload();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object getAttribute(EObject elem, String attributeName) {
		return elem.eGet(elem.eClass().getEStructuralFeature(attributeName));
	}

	public static String getName(EObject elem) {
		return elem.eClass().getName();
	}

	public static EPackage getDataModelPackage(){
		EPackage totalPackage = (EPackage) dataMetamodel.getContents().get(0);
		return totalPackage;
	}

	public static EObject createClass(String className, ArrayList<EObject> attributes) {
		EPackage totalPackage = getDataModelPackage();
		EClass classModel = (EClass) totalPackage.getEClassifier("Class");
		EObject classObject = totalPackage.getEFactoryInstance().create(classModel);
		classObject.eSet(classModel.getEStructuralFeature("name"), className);
		classObject.eSet(classModel.getEStructuralFeature("attributes"), attributes);
		return classObject;
	}

	public static EObject createAttribute(Object name, Object type) {
		EPackage totalPackage = getDataModelPackage();
		EClass attributeModel = (EClass) totalPackage.getEClassifier("Attribute");
		EObject attributeObject = totalPackage.getEFactoryInstance().create(attributeModel);
		attributeObject.eSet(attributeModel.getEStructuralFeature("name"), name);
		attributeObject.eSet(attributeModel.getEStructuralFeature("type"), type);
		return attributeObject;
	}

	public static EObject createModel(ArrayList<EObject> classes) {
		EPackage totalPackage = getDataModelPackage();
		EClass modelModel = (EClass) totalPackage.getEClassifier("Model");
		EObject modelObject = totalPackage.getEFactoryInstance().create(modelModel);
		modelObject.eSet(modelModel.getEStructuralFeature("classes"), classes);
		return modelObject;
	}
}
