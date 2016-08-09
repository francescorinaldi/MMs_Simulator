package classes_package;

import java.io.File;




import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLparser {
	
	 public static void XMLOnDemand(ArrayList<VMOnDemand> singleVMs,ArrayList<Threshold> thresholds) {
		 
		 	VMOnDemand newVM;
			Threshold newThreshold;
			SingleVM newSingleVM;
		 
		    try {
		 
			File fXmlFile = new File("vmOnDemand_eu-west-1_linux_.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 
			doc.getDocumentElement().normalize();
		 		 
			NodeList nList = doc.getElementsByTagName("single_virtual_machine");
		 	
			//System.out.println(nList.getLength());
			
			
			//Fetch single VMS
			for (int temp = 0; temp < nList.getLength(); temp++) {
				 
				Node nNode = nList.item(temp);
				
				newVM = new VMOnDemand();
				
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
					//System.out.println("Nome VM : " +eElement.getElementsByTagName("name").item(0).getTextContent() +" con MU="+ eElement.getElementsByTagName("mu").item(0).getTextContent());
					
					newVM.name=eElement.getElementsByTagName("name").item(0).getTextContent();
					newVM.mu=Double.parseDouble(eElement.getElementsByTagName("mu").item(0).getTextContent());
					
					NodeList nl = eElement.getElementsByTagName("vm_payment");
				        
				        for(int x = 0;x < nl.getLength();x++)
				        {
				        	Node child_node = nl.item(x);
				        	if (nNode.getNodeType() == Node.ELEMENT_NODE) {
								Element child_eElement = (Element) child_node;

								//System.out.println("running payment:" + child_eElement.getAttribute("running_payment"));
								newVM.running_payment=Double.parseDouble(child_eElement.getAttribute("running_payment"));
				        	}

				        }
		 
				}
				singleVMs.add(newVM);
			}
			
			
			
			//Fetch the THRESHOLDS
			
			nList = doc.getElementsByTagName("threshold");
			//System.out.println(nList.getLength());

			for (int temp = 0; temp < nList.getLength(); temp++) {
				
				newThreshold = new Threshold();
		 
				Node nNode = nList.item(temp);
		 
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
		 
					//System.out.println("Soglia : " + eElement.getAttribute("workload"));
					newThreshold.WorkLoad=73.38+Double.parseDouble(eElement.getAttribute("workload"));
					newThreshold.Id=Integer.decode(eElement.getAttribute("id"));
					//System.out.println("Macchine da attivare:");
					NodeList nl = eElement.getElementsByTagName("on_demand");
				        
				        for(int x = 0;x < nl.getLength();x++)
				        {
				        	newSingleVM = new SingleVM();
				        	Node child_node = nl.item(x);
				        	if (nNode.getNodeType() == Node.ELEMENT_NODE) {
								Element child_eElement = (Element) child_node;

								//System.out.println("N°:" + child_eElement.getAttribute("num")+" di tipo:"+ child_eElement.getAttribute("vm_name"));
								newSingleVM.num=Integer.decode(child_eElement.getAttribute("num"));
								newSingleVM.vm_name =child_eElement.getAttribute("vm_name");
								newThreshold.VMsToActivate.add(newSingleVM);
				        	}

				        }
		 
				}
				thresholds.add(newThreshold);
			}
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		  }

	 public static void XMLReserved(ArrayList<VMReservedType> typeOfReservedVMs,ArrayList<Threshold> thresholds){
		//da rendere più generico, funzionante solo con file generati per log campionati del mondo98
		 VMReservedType newVMReserved;
		 Threshold newThreshold;
		 VMReservedPayment vm_payment;
		 
		 try { 
				File fXmlFile = new File("vmReserved_eu-west-1_linux_c1-xlarge.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
			 
				doc.getDocumentElement().normalize();
			 		 
				NodeList nList = doc.getElementsByTagName("single_virtual_machine");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					 
					Node nNode = nList.item(temp);
					
					newVMReserved = new VMReservedType();
			 
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			 
						Element eElement = (Element) nNode;
						
						newVMReserved.name=eElement.getElementsByTagName("name").item(0).getTextContent();
						newVMReserved.region=eElement.getElementsByTagName("region").item(0).getTextContent();
						newVMReserved.provider=eElement.getElementsByTagName("provider").item(0).getTextContent();
						newVMReserved.reservation_period=eElement.getElementsByTagName("reservation_period").item(0).getTextContent();
						newVMReserved.os=eElement.getElementsByTagName("os").item(0).getTextContent();
						newVMReserved.mu=Double.parseDouble(eElement.getElementsByTagName("mu").item(0).getTextContent());
						
						NodeList nl = eElement.getElementsByTagName("vm_payment");
				        
				        for(int x = 0;x < nl.getLength();x++)
				        {	
							vm_payment = new VMReservedPayment();

				        	Node child_node = nl.item(x);
				        	if (nNode.getNodeType() == Node.ELEMENT_NODE) {
								Element child_eElement = (Element) child_node;

								//System.out.println("running payment:" + child_eElement.getAttribute("running_payment"));
								vm_payment.running_payment=Double.parseDouble(child_eElement.getAttribute("running_payment"));
								vm_payment.periodic_payment=Double.parseDouble(child_eElement.getAttribute("periodic_payment"));
								vm_payment.reservation_type=child_eElement.getAttribute("reservation_type");
				        	}
				        	newVMReserved.VMPayments.add(vm_payment);

				        }
						typeOfReservedVMs.add(newVMReserved);
					}
				}
				nList = doc.getElementsByTagName("reserved");
				for (int temp = 0; temp < nList.getLength(); temp++) {
						 
						Node nNode = nList.item(temp);
						
						newThreshold =new Threshold();
				 
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement = (Element) nNode;
							newThreshold.WorkLoad=Double.parseDouble(eElement.getAttribute("workload"));
							newThreshold.Id=Integer.decode(eElement.getAttribute("id"));
				        	SingleVM newVM = new SingleVM();
				        	newVM.num=1;
				        	newVM.vm_name ="c1-xlarge";
				        	newVM.r=1;
				        	newVM.reservationType=eElement.getAttribute("reservation_type");
							newThreshold.VMsToActivate.add(newVM);
						}
						thresholds.add(newThreshold);
				}
				
				
		 }catch (Exception e) {
				e.printStackTrace();
		    }
	 }
	 
}
