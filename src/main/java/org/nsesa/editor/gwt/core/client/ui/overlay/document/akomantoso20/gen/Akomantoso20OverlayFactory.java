package org.nsesa.editor.gwt.core.client.ui.overlay.document.akomantoso20.gen;

import com.google.gwt.dom.client.Element;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;

/**
 * This file is generated.
 */
public class Akomantoso20OverlayFactory implements OverlayFactory {

    public AmendableWidget getAmendableWidget(final Element element) {
        if ("".equals(element.getTagName())) {
            throw new IllegalArgumentException("Empty element or null passed.");
        } else if ("summary".equalsIgnoreCase(element.getTagName())) {
            return new Summary(element);
        } else if ("supports".equalsIgnoreCase(element.getTagName())) {
            return new Supports(element);
        } else if ("party".equalsIgnoreCase(element.getTagName())) {
            return new Party(element);
        } else if ("FRBRalias".equalsIgnoreCase(element.getTagName())) {
            return new FRBRalias(element);
        } else if ("preface".equalsIgnoreCase(element.getTagName())) {
            return new Preface(element);
        } else if ("references".equalsIgnoreCase(element.getTagName())) {
            return new References(element);
        } else if ("FRBRlanguage".equalsIgnoreCase(element.getTagName())) {
            return new FRBRlanguage(element);
        } else if ("event".equalsIgnoreCase(element.getTagName())) {
            return new Event(element);
        } else if ("quotedStructure".equalsIgnoreCase(element.getTagName())) {
            return new QuotedStructure(element);
        } else if ("role".equalsIgnoreCase(element.getTagName())) {
            return new Role(element);
        } else if ("judgement".equalsIgnoreCase(element.getTagName())) {
            return new Judgement(element);
        } else if ("quantity".equalsIgnoreCase(element.getTagName())) {
            return new Quantity(element);
        } else if ("quorumVerification".equalsIgnoreCase(element.getTagName())) {
            return new QuorumVerification(element);
        } else if ("extractStructure".equalsIgnoreCase(element.getTagName())) {
            return new ExtractStructure(element);
        } else if ("fillIn".equalsIgnoreCase(element.getTagName())) {
            return new FillIn(element);
        } else if ("signature".equalsIgnoreCase(element.getTagName())) {
            return new Signature(element);
        } else if ("componentRef".equalsIgnoreCase(element.getTagName())) {
            return new ComponentRef(element);
        } else if ("administrationOfOath".equalsIgnoreCase(element.getTagName())) {
            return new AdministrationOfOath(element);
        } else if ("vote".equalsIgnoreCase(element.getTagName())) {
            return new Vote(element);
        } else if ("mod".equalsIgnoreCase(element.getTagName())) {
            return new Mod(element);
        } else if ("answer".equalsIgnoreCase(element.getTagName())) {
            return new Answer(element);
        } else if ("debateBody".equalsIgnoreCase(element.getTagName())) {
            return new DebateBody(element);
        } else if ("papers".equalsIgnoreCase(element.getTagName())) {
            return new Papers(element);
        } else if ("noteRef".equalsIgnoreCase(element.getTagName())) {
            return new NoteRef(element);
        } else if ("header".equalsIgnoreCase(element.getTagName())) {
            return new Header(element);
        } else if ("proceduralMotions".equalsIgnoreCase(element.getTagName())) {
            return new ProceduralMotions(element);
        } else if ("listIntroduction".equalsIgnoreCase(element.getTagName())) {
            return new ListIntroduction(element);
        } else if ("content".equalsIgnoreCase(element.getTagName())) {
            return new Content(element);
        } else if ("rref".equalsIgnoreCase(element.getTagName())) {
            return new Rref(element);
        } else if ("nationalInterest".equalsIgnoreCase(element.getTagName())) {
            return new NationalInterest(element);
        } else if ("contrasts".equalsIgnoreCase(element.getTagName())) {
            return new Contrasts(element);
        } else if ("components".equalsIgnoreCase(element.getTagName())) {
            return new Components(element);
        } else if ("docPurpose".equalsIgnoreCase(element.getTagName())) {
            return new DocPurpose(element);
        } else if ("introduction".equalsIgnoreCase(element.getTagName())) {
            return new Introduction(element);
        } else if ("TLCEvent".equalsIgnoreCase(element.getTagName())) {
            return new TLCEvent(element);
        } else if ("tome".equalsIgnoreCase(element.getTagName())) {
            return new Tome(element);
        } else if ("TLCLocation".equalsIgnoreCase(element.getTagName())) {
            return new TLCLocation(element);
        } else if ("date".equalsIgnoreCase(element.getTagName())) {
            return new Date(element);
        } else if ("meta".equalsIgnoreCase(element.getTagName())) {
            return new Meta(element);
        } else if ("passiveRef".equalsIgnoreCase(element.getTagName())) {
            return new PassiveRef(element);
        } else if ("division".equalsIgnoreCase(element.getTagName())) {
            return new Division(element);
        } else if ("activeModifications".equalsIgnoreCase(element.getTagName())) {
            return new ActiveModifications(element);
        } else if ("quotedText".equalsIgnoreCase(element.getTagName())) {
            return new QuotedText(element);
        } else if ("popup".equalsIgnoreCase(element.getTagName())) {
            return new Popup(element);
        } else if ("inline".equalsIgnoreCase(element.getTagName())) {
            return new Inline(element);
        } else if ("domain".equalsIgnoreCase(element.getTagName())) {
            return new Domain(element);
        } else if ("debate".equalsIgnoreCase(element.getTagName())) {
            return new Debate(element);
        } else if ("activeRef".equalsIgnoreCase(element.getTagName())) {
            return new ActiveRef(element);
        } else if ("relatedDocument".equalsIgnoreCase(element.getTagName())) {
            return new RelatedDocument(element);
        } else if ("docIntroducer".equalsIgnoreCase(element.getTagName())) {
            return new DocIntroducer(element);
        } else if ("FRBRthis".equalsIgnoreCase(element.getTagName())) {
            return new FRBRthis(element);
        } else if ("ul".equalsIgnoreCase(element.getTagName())) {
            return new Ul(element);
        } else if ("b".equalsIgnoreCase(element.getTagName())) {
            return new B(element);
        } else if ("temporalData".equalsIgnoreCase(element.getTagName())) {
            return new TemporalData(element);
        } else if ("noticesOfMotion".equalsIgnoreCase(element.getTagName())) {
            return new NoticesOfMotion(element);
        } else if ("a".equalsIgnoreCase(element.getTagName())) {
            return new A(element);
        } else if ("intro".equalsIgnoreCase(element.getTagName())) {
            return new Intro(element);
        } else if ("FRBRExpression".equalsIgnoreCase(element.getTagName())) {
            return new FRBRExpression(element);
        } else if ("i".equalsIgnoreCase(element.getTagName())) {
            return new I(element);
        } else if ("ref".equalsIgnoreCase(element.getTagName())) {
            return new Ref(element);
        } else if ("new".equalsIgnoreCase(element.getTagName())) {
            return new New(element);
        } else if ("num".equalsIgnoreCase(element.getTagName())) {
            return new Num(element);
        } else if ("u".equalsIgnoreCase(element.getTagName())) {
            return new U(element);
        } else if ("amendmentJustification".equalsIgnoreCase(element.getTagName())) {
            return new AmendmentJustification(element);
        } else if ("restricts".equalsIgnoreCase(element.getTagName())) {
            return new Restricts(element);
        } else if ("background".equalsIgnoreCase(element.getTagName())) {
            return new Background(element);
        } else if ("p".equalsIgnoreCase(element.getTagName())) {
            return new P(element);
        } else if ("original".equalsIgnoreCase(element.getTagName())) {
            return new Original(element);
        } else if ("doc".equalsIgnoreCase(element.getTagName())) {
            return new Doc(element);
        } else if ("notes".equalsIgnoreCase(element.getTagName())) {
            return new Notes(element);
        } else if ("part".equalsIgnoreCase(element.getTagName())) {
            return new Part(element);
        } else if ("amendment".equalsIgnoreCase(element.getTagName())) {
            return new Amendment(element);
        } else if ("body".equalsIgnoreCase(element.getTagName())) {
            return new Body(element);
        } else if ("docType".equalsIgnoreCase(element.getTagName())) {
            return new DocType(element);
        } else if ("tr".equalsIgnoreCase(element.getTagName())) {
            return new Tr(element);
        } else if ("change".equalsIgnoreCase(element.getTagName())) {
            return new Change(element);
        } else if ("entity".equalsIgnoreCase(element.getTagName())) {
            return new Entity(element);
        } else if ("authorialNote".equalsIgnoreCase(element.getTagName())) {
            return new AuthorialNote(element);
        } else if ("td".equalsIgnoreCase(element.getTagName())) {
            return new Td(element);
        } else if ("br".equalsIgnoreCase(element.getTagName())) {
            return new Br(element);
        } else if ("th".equalsIgnoreCase(element.getTagName())) {
            return new Th(element);
        } else if ("component".equalsIgnoreCase(element.getTagName())) {
            return new Component(element);
        } else if ("eol".equalsIgnoreCase(element.getTagName())) {
            return new Eol(element);
        } else if ("adjournment".equalsIgnoreCase(element.getTagName())) {
            return new Adjournment(element);
        } else if ("opinion".equalsIgnoreCase(element.getTagName())) {
            return new Opinion(element);
        } else if ("eop".equalsIgnoreCase(element.getTagName())) {
            return new Eop(element);
        } else if ("putsInQuestion".equalsIgnoreCase(element.getTagName())) {
            return new PutsInQuestion(element);
        } else if ("docStage".equalsIgnoreCase(element.getTagName())) {
            return new DocStage(element);
        } else if ("subchapter".equalsIgnoreCase(element.getTagName())) {
            return new Subchapter(element);
        } else if ("amendmentList".equalsIgnoreCase(element.getTagName())) {
            return new AmendmentList(element);
        } else if ("docCommittee".equalsIgnoreCase(element.getTagName())) {
            return new DocCommittee(element);
        } else if ("condition".equalsIgnoreCase(element.getTagName())) {
            return new Condition(element);
        } else if ("FRBRItem".equalsIgnoreCase(element.getTagName())) {
            return new FRBRItem(element);
        } else if ("amendmentReference".equalsIgnoreCase(element.getTagName())) {
            return new AmendmentReference(element);
        } else if ("efficacy".equalsIgnoreCase(element.getTagName())) {
            return new Efficacy(element);
        } else if ("otherAnalysis".equalsIgnoreCase(element.getTagName())) {
            return new OtherAnalysis(element);
        } else if ("docNumber".equalsIgnoreCase(element.getTagName())) {
            return new DocNumber(element);
        } else if ("workflow".equalsIgnoreCase(element.getTagName())) {
            return new Workflow(element);
        } else if ("FRBRManifestation".equalsIgnoreCase(element.getTagName())) {
            return new FRBRManifestation(element);
        } else if ("speech".equalsIgnoreCase(element.getTagName())) {
            return new Speech(element);
        } else if ("mainBody".equalsIgnoreCase(element.getTagName())) {
            return new MainBody(element);
        } else if ("foreign".equalsIgnoreCase(element.getTagName())) {
            return new Foreign(element);
        } else if ("extractText".equalsIgnoreCase(element.getTagName())) {
            return new ExtractText(element);
        } else if ("span".equalsIgnoreCase(element.getTagName())) {
            return new Span(element);
        } else if ("subdivision".equalsIgnoreCase(element.getTagName())) {
            return new Subdivision(element);
        } else if ("result".equalsIgnoreCase(element.getTagName())) {
            return new Result(element);
        } else if ("quorum".equalsIgnoreCase(element.getTagName())) {
            return new Quorum(element);
        } else if ("sub".equalsIgnoreCase(element.getTagName())) {
            return new Sub(element);
        } else if ("toc".equalsIgnoreCase(element.getTagName())) {
            return new Toc(element);
        } else if ("bill".equalsIgnoreCase(element.getTagName())) {
            return new Bill(element);
        } else if ("subsection".equalsIgnoreCase(element.getTagName())) {
            return new Subsection(element);
        } else if ("transitional".equalsIgnoreCase(element.getTagName())) {
            return new Transitional(element);
        } else if ("analysis".equalsIgnoreCase(element.getTagName())) {
            return new Analysis(element);
        } else if ("sup".equalsIgnoreCase(element.getTagName())) {
            return new Sup(element);
        } else if ("TLCConcept".equalsIgnoreCase(element.getTagName())) {
            return new TLCConcept(element);
        } else if ("jurisprudence".equalsIgnoreCase(element.getTagName())) {
            return new Jurisprudence(element);
        } else if ("chapter".equalsIgnoreCase(element.getTagName())) {
            return new Chapter(element);
        } else if ("organization".equalsIgnoreCase(element.getTagName())) {
            return new Organization(element);
        } else if ("docketNumber".equalsIgnoreCase(element.getTagName())) {
            return new DocketNumber(element);
        } else if ("debateReport".equalsIgnoreCase(element.getTagName())) {
            return new DebateReport(element);
        } else if ("question".equalsIgnoreCase(element.getTagName())) {
            return new Question(element);
        } else if ("voting".equalsIgnoreCase(element.getTagName())) {
            return new Voting(element);
        } else if ("eventRef".equalsIgnoreCase(element.getTagName())) {
            return new EventRef(element);
        } else if ("fragment".equalsIgnoreCase(element.getTagName())) {
            return new Fragment(element);
        } else if ("mref".equalsIgnoreCase(element.getTagName())) {
            return new Mref(element);
        } else if ("timeInterval".equalsIgnoreCase(element.getTagName())) {
            return new TimeInterval(element);
        } else if ("communication".equalsIgnoreCase(element.getTagName())) {
            return new Communication(element);
        } else if ("recount".equalsIgnoreCase(element.getTagName())) {
            return new Recount(element);
        } else if ("coverPage".equalsIgnoreCase(element.getTagName())) {
            return new CoverPage(element);
        } else if ("collectionBody".equalsIgnoreCase(element.getTagName())) {
            return new CollectionBody(element);
        } else if ("attachmentOf".equalsIgnoreCase(element.getTagName())) {
            return new AttachmentOf(element);
        } else if ("TLCObject".equalsIgnoreCase(element.getTagName())) {
            return new TLCObject(element);
        } else if ("source".equalsIgnoreCase(element.getTagName())) {
            return new Source(element);
        } else if ("FRBRname".equalsIgnoreCase(element.getTagName())) {
            return new FRBRname(element);
        } else if ("clause".equalsIgnoreCase(element.getTagName())) {
            return new Clause(element);
        } else if ("person".equalsIgnoreCase(element.getTagName())) {
            return new Person(element);
        } else if ("TLCPerson".equalsIgnoreCase(element.getTagName())) {
            return new TLCPerson(element);
        } else if ("citations".equalsIgnoreCase(element.getTagName())) {
            return new Citations(element);
        } else if ("paragraph".equalsIgnoreCase(element.getTagName())) {
            return new Paragraph(element);
        } else if ("neutralCitation".equalsIgnoreCase(element.getTagName())) {
            return new NeutralCitation(element);
        } else if ("docStatus".equalsIgnoreCase(element.getTagName())) {
            return new DocStatus(element);
        } else if ("applies".equalsIgnoreCase(element.getTagName())) {
            return new Applies(element);
        } else if ("motivation".equalsIgnoreCase(element.getTagName())) {
            return new Motivation(element);
        } else if ("TLCReference".equalsIgnoreCase(element.getTagName())) {
            return new TLCReference(element);
        } else if ("legislature".equalsIgnoreCase(element.getTagName())) {
            return new Legislature(element);
        } else if ("oralStatements".equalsIgnoreCase(element.getTagName())) {
            return new OralStatements(element);
        } else if ("recitals".equalsIgnoreCase(element.getTagName())) {
            return new Recitals(element);
        } else if ("lifecycle".equalsIgnoreCase(element.getTagName())) {
            return new Lifecycle(element);
        } else if ("FRBRuri".equalsIgnoreCase(element.getTagName())) {
            return new FRBRuri(element);
        } else if ("preamble".equalsIgnoreCase(element.getTagName())) {
            return new Preamble(element);
        } else if ("TLCRole".equalsIgnoreCase(element.getTagName())) {
            return new TLCRole(element);
        } else if ("ol".equalsIgnoreCase(element.getTagName())) {
            return new Ol(element);
        } else if ("amendmentContent".equalsIgnoreCase(element.getTagName())) {
            return new AmendmentContent(element);
        } else if ("citation".equalsIgnoreCase(element.getTagName())) {
            return new Citation(element);
        } else if ("mmod".equalsIgnoreCase(element.getTagName())) {
            return new Mmod(element);
        } else if ("FRBRsubtype".equalsIgnoreCase(element.getTagName())) {
            return new FRBRsubtype(element);
        } else if ("subclause".equalsIgnoreCase(element.getTagName())) {
            return new Subclause(element);
        } else if ("questions".equalsIgnoreCase(element.getTagName())) {
            return new Questions(element);
        } else if ("tocItem".equalsIgnoreCase(element.getTagName())) {
            return new TocItem(element);
        } else if ("table".equalsIgnoreCase(element.getTagName())) {
            return new Table(element);
        } else if ("sublist".equalsIgnoreCase(element.getTagName())) {
            return new Sublist(element);
        } else if ("destination".equalsIgnoreCase(element.getTagName())) {
            return new Destination(element);
        } else if ("session".equalsIgnoreCase(element.getTagName())) {
            return new Session(element);
        } else if ("textualMod".equalsIgnoreCase(element.getTagName())) {
            return new TextualMod(element);
        } else if ("recordedTime".equalsIgnoreCase(element.getTagName())) {
            return new RecordedTime(element);
        } else if ("container".equalsIgnoreCase(element.getTagName())) {
            return new Container(element);
        } else if ("longTitle".equalsIgnoreCase(element.getTagName())) {
            return new LongTitle(element);
        } else if ("scene".equalsIgnoreCase(element.getTagName())) {
            return new Scene(element);
        } else if ("distinguishes".equalsIgnoreCase(element.getTagName())) {
            return new Distinguishes(element);
        } else if ("heading".equalsIgnoreCase(element.getTagName())) {
            return new Heading(element);
        } else if ("subparagraph".equalsIgnoreCase(element.getTagName())) {
            return new Subparagraph(element);
        } else if ("debateSection".equalsIgnoreCase(element.getTagName())) {
            return new DebateSection(element);
        } else if ("block".equalsIgnoreCase(element.getTagName())) {
            return new Block(element);
        } else if ("docJurisdiction".equalsIgnoreCase(element.getTagName())) {
            return new DocJurisdiction(element);
        } else if ("docProponent".equalsIgnoreCase(element.getTagName())) {
            return new DocProponent(element);
        } else if ("decision".equalsIgnoreCase(element.getTagName())) {
            return new Decision(element);
        } else if ("personalStatements".equalsIgnoreCase(element.getTagName())) {
            return new PersonalStatements(element);
        } else if ("omissis".equalsIgnoreCase(element.getTagName())) {
            return new Omissis(element);
        } else if ("rmod".equalsIgnoreCase(element.getTagName())) {
            return new Rmod(element);
        } else if ("title".equalsIgnoreCase(element.getTagName())) {
            return new Title(element);
        } else if ("componentData".equalsIgnoreCase(element.getTagName())) {
            return new ComponentData(element);
        } else if ("def".equalsIgnoreCase(element.getTagName())) {
            return new Def(element);
        } else if ("judge".equalsIgnoreCase(element.getTagName())) {
            return new Judge(element);
        } else if ("docTitle".equalsIgnoreCase(element.getTagName())) {
            return new DocTitle(element);
        } else if ("isAnalogTo".equalsIgnoreCase(element.getTagName())) {
            return new IsAnalogTo(element);
        } else if ("del".equalsIgnoreCase(element.getTagName())) {
            return new Del(element);
        } else if ("FRBRdate".equalsIgnoreCase(element.getTagName())) {
            return new FRBRdate(element);
        } else if ("affectedDocument".equalsIgnoreCase(element.getTagName())) {
            return new AffectedDocument(element);
        } else if ("note".equalsIgnoreCase(element.getTagName())) {
            return new Note(element);
        } else if ("dissentsFrom".equalsIgnoreCase(element.getTagName())) {
            return new DissentsFrom(element);
        } else if ("TLCProcess".equalsIgnoreCase(element.getTagName())) {
            return new TLCProcess(element);
        } else if ("passiveModifications".equalsIgnoreCase(element.getTagName())) {
            return new PassiveModifications(element);
        } else if ("placeholder".equalsIgnoreCase(element.getTagName())) {
            return new Placeholder(element);
        } else if ("caption".equalsIgnoreCase(element.getTagName())) {
            return new Caption(element);
        } else if ("outcome".equalsIgnoreCase(element.getTagName())) {
            return new Outcome(element);
        } else if ("documentCollection".equalsIgnoreCase(element.getTagName())) {
            return new DocumentCollection(element);
        } else if ("process".equalsIgnoreCase(element.getTagName())) {
            return new Process(element);
        } else if ("point".equalsIgnoreCase(element.getTagName())) {
            return new Point(element);
        } else if ("article".equalsIgnoreCase(element.getTagName())) {
            return new Article(element);
        } else if ("shortTitle".equalsIgnoreCase(element.getTagName())) {
            return new ShortTitle(element);
        } else if ("proprietary".equalsIgnoreCase(element.getTagName())) {
            return new Proprietary(element);
        } else if ("classification".equalsIgnoreCase(element.getTagName())) {
            return new Classification(element);
        } else if ("item".equalsIgnoreCase(element.getTagName())) {
            return new Item(element);
        } else if ("hcontainer".equalsIgnoreCase(element.getTagName())) {
            return new Hcontainer(element);
        } else if ("pointOfOrder".equalsIgnoreCase(element.getTagName())) {
            return new PointOfOrder(element);
        } else if ("fragmentBody".equalsIgnoreCase(element.getTagName())) {
            return new FragmentBody(element);
        } else if ("force".equalsIgnoreCase(element.getTagName())) {
            return new Force(element);
        } else if ("old".equalsIgnoreCase(element.getTagName())) {
            return new Old(element);
        } else if ("count".equalsIgnoreCase(element.getTagName())) {
            return new Count(element);
        } else if ("scopeMod".equalsIgnoreCase(element.getTagName())) {
            return new ScopeMod(element);
        } else if ("tblock".equalsIgnoreCase(element.getTagName())) {
            return new Tblock(element);
        } else if ("location".equalsIgnoreCase(element.getTagName())) {
            return new Location(element);
        } else if ("remark".equalsIgnoreCase(element.getTagName())) {
            return new Remark(element);
        } else if ("narrative".equalsIgnoreCase(element.getTagName())) {
            return new Narrative(element);
        } else if ("officialGazette".equalsIgnoreCase(element.getTagName())) {
            return new OfficialGazette(element);
        } else if ("img".equalsIgnoreCase(element.getTagName())) {
            return new Img(element);
        } else if ("listConclusion".equalsIgnoreCase(element.getTagName())) {
            return new ListConclusion(element);
        } else if ("overrules".equalsIgnoreCase(element.getTagName())) {
            return new Overrules(element);
        } else if ("derogates".equalsIgnoreCase(element.getTagName())) {
            return new Derogates(element);
        } else if ("alinea".equalsIgnoreCase(element.getTagName())) {
            return new Alinea(element);
        } else if ("blockList".equalsIgnoreCase(element.getTagName())) {
            return new BlockList(element);
        } else if ("declarationOfVote".equalsIgnoreCase(element.getTagName())) {
            return new DeclarationOfVote(element);
        } else if ("temporalGroup".equalsIgnoreCase(element.getTagName())) {
            return new TemporalGroup(element);
        } else if ("legalSystemMod".equalsIgnoreCase(element.getTagName())) {
            return new LegalSystemMod(element);
        } else if ("petitions".equalsIgnoreCase(element.getTagName())) {
            return new Petitions(element);
        } else if ("wrap".equalsIgnoreCase(element.getTagName())) {
            return new Wrap(element);
        } else if ("indent".equalsIgnoreCase(element.getTagName())) {
            return new Indent(element);
        } else if ("extends".equalsIgnoreCase(element.getTagName())) {
            return new Extends(element);
        } else if ("hasAttachment".equalsIgnoreCase(element.getTagName())) {
            return new HasAttachment(element);
        } else if ("ins".equalsIgnoreCase(element.getTagName())) {
            return new Ins(element);
        } else if ("amendmentBody".equalsIgnoreCase(element.getTagName())) {
            return new AmendmentBody(element);
        } else if ("li".equalsIgnoreCase(element.getTagName())) {
            return new Li(element);
        } else if ("parliamentary".equalsIgnoreCase(element.getTagName())) {
            return new Parliamentary(element);
        } else if ("presentation".equalsIgnoreCase(element.getTagName())) {
            return new Presentation(element);
        } else if ("marker".equalsIgnoreCase(element.getTagName())) {
            return new Marker(element);
        } else if ("meaningMod".equalsIgnoreCase(element.getTagName())) {
            return new MeaningMod(element);
        } else if ("amendmentHeading".equalsIgnoreCase(element.getTagName())) {
            return new AmendmentHeading(element);
        } else if ("FRBRauthor".equalsIgnoreCase(element.getTagName())) {
            return new FRBRauthor(element);
        } else if ("lawyer".equalsIgnoreCase(element.getTagName())) {
            return new Lawyer(element);
        } else if ("concept".equalsIgnoreCase(element.getTagName())) {
            return new Concept(element);
        } else if ("other".equalsIgnoreCase(element.getTagName())) {
            return new Other(element);
        } else if ("componentInfo".equalsIgnoreCase(element.getTagName())) {
            return new ComponentInfo(element);
        } else if ("subheading".equalsIgnoreCase(element.getTagName())) {
            return new Subheading(element);
        } else if ("courtType".equalsIgnoreCase(element.getTagName())) {
            return new CourtType(element);
        } else if ("div".equalsIgnoreCase(element.getTagName())) {
            return new Div(element);
        } else if ("resolutions".equalsIgnoreCase(element.getTagName())) {
            return new Resolutions(element);
        } else if ("from".equalsIgnoreCase(element.getTagName())) {
            return new From(element);
        } else if ("object".equalsIgnoreCase(element.getTagName())) {
            return new Object(element);
        } else if ("interstitial".equalsIgnoreCase(element.getTagName())) {
            return new Interstitial(element);
        } else if ("recital".equalsIgnoreCase(element.getTagName())) {
            return new Recital(element);
        } else if ("judicial".equalsIgnoreCase(element.getTagName())) {
            return new Judicial(element);
        } else if ("FRBRcountry".equalsIgnoreCase(element.getTagName())) {
            return new FRBRcountry(element);
        } else if ("efficacyMod".equalsIgnoreCase(element.getTagName())) {
            return new EfficacyMod(element);
        } else if ("rollCall".equalsIgnoreCase(element.getTagName())) {
            return new RollCall(element);
        } else if ("FRBRformat".equalsIgnoreCase(element.getTagName())) {
            return new FRBRformat(element);
        } else if ("docDate".equalsIgnoreCase(element.getTagName())) {
            return new DocDate(element);
        } else if ("attachments".equalsIgnoreCase(element.getTagName())) {
            return new Attachments(element);
        } else if ("TLCOrganization".equalsIgnoreCase(element.getTagName())) {
            return new TLCOrganization(element);
        } else if ("application".equalsIgnoreCase(element.getTagName())) {
            return new Application(element);
        } else if ("FRBRWork".equalsIgnoreCase(element.getTagName())) {
            return new FRBRWork(element);
        } else if ("akomaNtoso".equalsIgnoreCase(element.getTagName())) {
            return new AkomaNtoso(element);
        } else if ("forceMod".equalsIgnoreCase(element.getTagName())) {
            return new ForceMod(element);
        } else if ("keyword".equalsIgnoreCase(element.getTagName())) {
            return new Keyword(element);
        } else if ("FRBRnumber".equalsIgnoreCase(element.getTagName())) {
            return new FRBRnumber(element);
        } else if ("list".equalsIgnoreCase(element.getTagName())) {
            return new List(element);
        } else if ("section".equalsIgnoreCase(element.getTagName())) {
            return new Section(element);
        } else if ("publication".equalsIgnoreCase(element.getTagName())) {
            return new Publication(element);
        } else if ("FRBRtranslation".equalsIgnoreCase(element.getTagName())) {
            return new FRBRtranslation(element);
        } else if ("TLCTerm".equalsIgnoreCase(element.getTagName())) {
            return new TLCTerm(element);
        } else if ("conclusions".equalsIgnoreCase(element.getTagName())) {
            return new Conclusions(element);
        } else if ("duration".equalsIgnoreCase(element.getTagName())) {
            return new Duration(element);
        } else if ("judgementBody".equalsIgnoreCase(element.getTagName())) {
            return new JudgementBody(element);
        } else if ("subpart".equalsIgnoreCase(element.getTagName())) {
            return new Subpart(element);
        } else if ("address".equalsIgnoreCase(element.getTagName())) {
            return new Address(element);
        } else if ("term".equalsIgnoreCase(element.getTagName())) {
            return new Term(element);
        } else if ("identification".equalsIgnoreCase(element.getTagName())) {
            return new Identification(element);
        } else if ("writtenStatements".equalsIgnoreCase(element.getTagName())) {
            return new WrittenStatements(element);
        } else if ("subtitle".equalsIgnoreCase(element.getTagName())) {
            return new Subtitle(element);
        } else if ("book".equalsIgnoreCase(element.getTagName())) {
            return new Book(element);
        } else if ("prayers".equalsIgnoreCase(element.getTagName())) {
            return new Prayers(element);
        } else if ("ministerialStatements".equalsIgnoreCase(element.getTagName())) {
            return new MinisterialStatements(element);
        } else if ("act".equalsIgnoreCase(element.getTagName())) {
            return new Act(element);
        } else if ("step".equalsIgnoreCase(element.getTagName())) {
            return new Step(element);
        } else if ("preservation".equalsIgnoreCase(element.getTagName())) {
            return new Preservation(element);
        } else if ("formula".equalsIgnoreCase(element.getTagName())) {
            return new Formula(element);
        }

// nothing found
        return null;
    }
}