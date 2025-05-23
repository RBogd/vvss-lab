package repository;

import domain.Nota;
import domain.Student;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NotaXMLRepository extends AbstractXMLRepository<String, Nota> {

    public NotaXMLRepository(NotaValidator validator, String XMLfilename) {
        super(validator, XMLfilename);
        loadFromXmlFile();
    }

    protected Element getElementFromEntity(Nota nota, Document XMLdocument) {
        Element element = XMLdocument.createElement("nota");
        element.setAttribute("IDStudent", nota.getIdStudent());
        element.setAttribute("IDTema", nota.getID());

        element.appendChild(createElement(XMLdocument, "Nota", String.valueOf(nota.getNota())));
        element.appendChild(createElement(XMLdocument, "SaptamanaPredare", String.valueOf(nota.getSaptamanaPredare())));
        element.appendChild(createElement(XMLdocument, "Feedback", nota.getFeedback()));

        return element;
    }

    protected Nota getEntityFromElement(Element node) {
        String IDStudent = node.getAttributeNode("IDStudent").getValue();
        String IDTema= node.getAttributeNode("IDTema").getValue();
        double nota = Double.parseDouble(node.getElementsByTagName("Nota").item(0).getTextContent());
        int saptamanaPredare = Integer.parseInt(node.getElementsByTagName("SaptamanaPredare").item(0).getTextContent());
        String feedback = node.getElementsByTagName("Feedback").item(0).getTextContent();
        String idNota = IDStudent + "#" + IDTema;
        return new Nota(idNota, nota, saptamanaPredare, feedback, IDStudent, IDTema);
    }

    public void createFile(Nota notaObj) {
        String idStudent = notaObj.getIdStudent();
        StudentValidator sval = new StudentValidator();
        TemaValidator tval = new TemaValidator();
        StudentFileRepository srepo = new StudentFileRepository(sval, "studenti.txt");
        TemaFileRepository trepo = new TemaFileRepository(tval, "teme.txt");

        Student student = srepo.findOne(idStudent);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(student.getNume() + ".txt", false))) {
            super.findAll().forEach(nota -> {
                if (nota.getIdStudent().equals(idStudent)) {
                    try {
                        bw.write("Tema: " + nota.getID() + "\n");
                        bw.write("Nota: " + nota.getNota() + "\n");
                        bw.write("Predata in saptamana: " + nota.getSaptamanaPredare() + "\n");
                        bw.write("Deadline: " + trepo.findOne(nota.getID()).getDeadline() + "\n");
                        bw.write("Feedback: " + nota.getFeedback() + "\n\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
//    public void createFile(Nota notaObj) {
//        String idStudent = notaObj.getIdStudent();
//        StudentValidator sval = new StudentValidator();
//        TemaValidator tval = new TemaValidator();
//        StudentXMLRepository srepo = new StudentXMLRepository(sval, "studenti.xml");
//        TemaXMLRepository trepo = new TemaXMLRepository(tval, "teme.xml");
//
//        Student student = srepo.findOne(idStudent);
//        try {
//            Document XMLdocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
//            Element root = XMLdocument.createElement("NoteStudent");
//            XMLdocument.appendChild(root);
//
//            super.findAll().forEach(nota -> {
//                if (nota.getIdStudent().equals(idStudent)) {
//                    try {
//                        Document XMLstudent = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
//                        Element element = XMLstudent.createElement("nota");
//
//                        Document XMLdocument2 = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(trepo.XMLfilename);
//                        Node n = XMLdocument2.getFirstChild();
//                        Node temaNode = XMLstudent.importNode(XMLdocument2, true);
//                        Tema t = trepo.getEntityFromNode((Element) temaNode);
//
//                        element.appendChild(createElement(XMLstudent, "Tema", t.getID()));
//                        element.appendChild(createElement(XMLstudent, "Nota", String.valueOf(nota.getNota())));
//                        element.appendChild(createElement(XMLstudent, "SaptamanaPredare", String.valueOf(nota.getSaptamanaPredare())));
//                        element.appendChild(createElement(XMLstudent, "Deadline", String.valueOf(t.getDeadline())));
//                        element.appendChild(createElement(XMLstudent, "Feedback", nota.getFeedback()));
//
//                        root.appendChild(element);
//
//                    } catch (ParserConfigurationException e) {
//                        e.printStackTrace();
//                    } catch (SAXException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//
//            Transformer XMLtransformer = TransformerFactory.newInstance().newTransformer();
//            XMLtransformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            XMLtransformer.transform(new DOMSource(XMLdocument), new StreamResult(XMLfilename));
//        }
//        catch(ParserConfigurationException pce) {
//            pce.printStackTrace();
//        }
//        catch(TransformerConfigurationException tce) {
//            tce.printStackTrace();
//        }
//        catch(TransformerException te) {
//            te.printStackTrace();
//        }
//    }}
