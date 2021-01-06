/*
 * Copyright (c) 2012, Ben Fortuna
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  o Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  o Neither the name of Ben Fortuna nor the names of any other contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.fortuna.ical4j.model.parameter;

import net.fortuna.ical4j.model.Content;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactory;
import net.fortuna.ical4j.util.Strings;

import java.net.URISyntaxException;

/**
 * $Id$ [18-Apr-2004]
 * <p/>
 * Defines a Link Relationship Type parameter.
 *
 * @author Mike Douglass
 */
public class Linkrel extends Parameter {

    private static final long serialVersionUID = 5346030888832899016L;

    public final static String  linkrel_about = "about";
    /* Refers to a resource that is the subject of the link's context.,"[RFC6903], section 2" */

    public final static String  linkrel_alternate = "alternate";
    /* Refers to a substitute for this context,[javax.swing.text.html.HTML] */

    public final static String  linkrel_amphtml = "amphtml";
    /* "Used to reference alternative content that uses the AMP profile of the HTML format.,[AMP HTML] */

    public final static String  linkrel_appendix = "appendix";
    /* Refers to an appendix.,[HTML 4.01 Specification] */

    public final static String  linkrel_appleTouchIcon = "apple-touch-icon";
    /* "Refers to an icon for the context. Synonym for icon.,[Configuring Web Applications] */

    public final static String linkrel_appleTouchStartupImage = "apple-touch-startup-image";
    /* Refers to a launch screen for the context.,[Configuring Web Applications] */

    public final static String linkrel_archives = "archives";
    /* "Refers to a collection of records, documents, or other materials of historical interest.",[HTML5] */

    public final static String linkrel_author = "author";
    /* Refers to the context's author.,[HTML] */

    public final static String linkrel_blockedBy = "blocked-by";
    /* "Identifies the entity that blocks access to a resource
    following receipt of a legal demand.",[RFC7725] */

    public final static String linkrel_bookmark = "bookmark";
    /* Gives a permanent link to use for bookmarking purposes.,[HTML] */

    public final static String linkrel_canonical = "canonical";
    /* Designates the preferred version of a resource (the IRI and its contents).,[RFC6596] */

    public final static String linkrel_chapter = "chapter";
    /* Refers to a chapter in a collection of resources.,[HTML 4.01 Specification] */

    public final static String linkrel_citeAs = "cite-as";
    /* Indicates that the link target is preferred over the link context for the purpose of permanent citation.,[RFC8574] */

    public final static String linkrel_collection = "collection";   /* The target IRI points to a resource which represents the collection resource for the context IRI.,[RFC6573] */

    public final static String linkrel_contents = "contents";
    /* Refers to a table of contents.,[HTML 4.01 Specification] */

    public final static String linkrel_convertedFrom = "convertedFrom";
    /* The document linked to was later converted to the
       document that contains this link relation.  For example, an RFC can
       have a link to the Internet-Draft that became the RFC; in that case,
       the link relation would be ""convertedFrom"".",[RFC7991],

       This relation is different than ""predecessor-version"" in that
       "predecessor-version" is for items in a version control system.  It
       is also different than ""previous"" in that this relation is used for
       converted resources";   /*  not those that are part of a sequence of
       resources. */

    public final static String linkrel_copyright = "copyright";
    /* "Refers to a copyright statement that applies to the
    link's context.",[HTML 4.01 Specification] */

    public final static String linkrel_createForm = "create-form";
    /* The target IRI points to a resource where a submission form
     can be obtained.,[RFC6861] */

    public final static String linkrel_current = "current";
    /* Refers to a resource containing the most recent
       item(s) in a collection of resources.",[RFC5005] */

    public final static String linkrel_describedby = "describedby";
    /* "Refers to a resource providing information about the
       link's context.",[Protocol for Web Description Resources (POWDER)] */

    public final static String linkrel_describes = "describes";
    /* The relationship A 'describes' B asserts that
       resource A provides a description of resource B. There are no
       constraints on the format or representation of either A or B,
       neither are there any further constraints on either resource.",[RFC6892],

       This link relation type is the inverse of the 'describedby'
       relation type.  While 'describedby' establishes a relation from
       the described resource back to the resource that describes it,
           'describes' established a relation from the describing resource to
       the resource it describes.  If B is 'describedby' A, then A
           'describes' B. */

    public final static String linkrel_disclosure = "disclosure";
    /* Refers to a list of patent disclosures made with respect to material for which 'disclosure' relation is specified.,[RFC6579] */

    public final static String linkrel_dnsPrefetch = "dns-prefetch";
    /* Used to indicate an origin that will be used to fetch required resources for the link context, and that the user agent ought to resolve as early as possible.",[Resource Hints] */

    public final static String linkrel_duplicate = "duplicate";
    /* "Refers to a resource whose available representations
    are byte-for-byte identical with the corresponding representations of
    the context IRI.",[RFC6249],"This relation is for static resources.  That is, an HTTP GET
    request on any duplicate will return the same representation.  It
    does not make sense for dynamic or POSTable resources and should not
    be used for them. */

    public final static String linkrel_edit = "edit";
    /* "Refers to a resource that can be used to edit the
    link's context.",[RFC5023] */

    public final static String linkrel_editForm = "edit-form";
    /* "The target IRI points to a resource where a submission form for
    editing associated resource can be obtained.",[RFC6861] */

    public final static String linkrel_editMedia = "edit-media";
    /* "Refers to a resource that can be used to edit media
    associated with the link's context.",[RFC5023] */

    public final static String linkrel_enclosure = "enclosure";
    /* "Identifies a related resource that is potentially
    large and might require special handling.",[RFC4287] */

    public final static String linkrel_external = "external";
    /* Refers to a resource that is not part of the same site as the current context.,[HTML] */

    public final static String linkrel_first = "first";
    /* "An IRI that refers to the furthest preceding resource
    in a series of resources.",[RFC8288],"This relation type registration did not indicate a
    reference.  Originally requested by Mark Nottingham in December
      2004. */

    public final static String linkrel_glossary = "glossary";
    /* Refers to a glossary of terms.,[HTML 4.01 Specification] */

    public final static String linkrel_help = "help";
    /* Refers to context-sensitive help.,[HTML] */

    public final static String linkrel_hosts = "hosts";
    /* "Refers to a resource hosted by the server indicated by
    the link context.",[RFC6690],"This relation is used in CoRE where links are retrieved as a
      ""/.well-known/core"" resource representation, and is the default
    relation type in the CoRE Link Format. */

    public final static String linkrel_hub = "hub";
    /* "Refers to a hub that enables registration for
    notification of updates to the context.",[WebSub],This relation type was requested by Brett Slatkin. */

    public final static String linkrel_icon = "icon";
    /* Refers to an icon representing the link's context.,[HTML] */

    public final static String linkrel_index = "index";
    /* Refers to an index.,[HTML 4.01 Specification] */

    public final static String linkrel_intervalAfter = "intervalAfter";
    /* refers to a resource associated with a time interval that ends before the beginning of the time interval associated with the context resource,[Time Ontology in OWL] section 4.2.21 */

    public final static String linkrel_intervalBefore = "intervalBefore";
    /* refers to a resource associated with a time interval that begins after the end of the time interval associated with the context resource,[Time Ontology in OWL] section 4.2.22 */

    public final static String linkrel_intervalContains = "intervalContains";
    /* "refers to a resource associated with a time interval that begins after the beginning of the time interval associated with the context resource, and ends before the end of the time interval associated with the context resource",[Time Ontology in OWL] section 4.2.23 */

    public final static String linkrel_intervalDisjoint = "intervalDisjoint";
    /* "refers to a resource associated with a time interval that begins after the end of the time interval associated with the context resource, or ends before the beginning of the time interval associated with the context resource",[Time Ontology in OWL] section 4.2.24 */

    public final static String linkrel_intervalDuring = "intervalDuring";
    /* "refers to a resource associated with a time interval that begins before the beginning of the time interval associated with the context resource, and ends after the end of the time interval associated with the context resource",[Time Ontology in OWL] section 4.2.25 */

    public final static String linkrel_intervalEquals = "intervalEquals";
    /* "refers to a resource associated with a time interval whose beginning coincides with the beginning of the time interval associated with the context resource, and whose end coincides with the end of the time interval associated with the context resource",[Time Ontology in OWL] section 4.2.26 */

    public final static String linkrel_intervalFinishedBy = "intervalFinishedBy";
    /* "refers to a resource associated with a time interval that begins after the beginning of the time interval associated with the context resource, and whose end coincides with the end of the time interval associated with the context resource",[Time Ontology in OWL] section 4.2.27 */

    public final static String linkrel_intervalFinishes = "intervalFinishes";
    /* "refers to a resource associated with a time interval that begins before the beginning of the time interval associated with the context resource, and whose end coincides with the end of the time interval associated with the context resource",[Time Ontology in OWL] section 4.2.28 */

    public final static String linkrel_intervalIn = "intervalIn";
    /* "refers to a resource associated with a time interval that begins before or is coincident with the beginning of the time interval associated with the context resource, and ends after or is coincident with the end of the time interval associated with the context resource",[Time Ontology in OWL] section 4.2.29 */

    public final static String linkrel_intervalMeets = "intervalMeets";
    /* refers to a resource associated with a time interval whose beginning coincides with the end of the time interval associated with the context resource,[Time Ontology in OWL] section 4.2.30 */

    public final static String linkrel_intervalMetBy = "intervalMetBy";
    /* refers to a resource associated with a time interval whose end coincides with the beginning of the time interval associated with the context resource,[Time Ontology in OWL] section 4.2.31 */

    public final static String linkrel_intervalOverlappedBy = "intervalOverlappedBy";
    /* "refers to a resource associated with a time interval that begins before the beginning of the time interval associated with the context resource, and ends after the beginning of the time interval associated with the context resource",[Time Ontology in OWL] section 4.2.32 */

    public final static String linkrel_intervalOverlaps = "intervalOverlaps";
    /* "refers to a resource associated with a time interval that begins before the end of the time interval associated with the context resource, and ends after the end of the time interval associated with the context resource",[Time Ontology in OWL] section 4.2.33 */

    public final static String linkrel_intervalStartedBy = "intervalStartedBy";
    /* "refers to a resource associated with a time interval whose beginning coincides with the beginning of the time interval associated with the context resource, and ends before the end of the time interval associated with the context resource",[Time Ontology in OWL] section 4.2.34 */

    public final static String linkrel_intervalStarts = "intervalStarts";
    /* "refers to a resource associated with a time interval whose beginning coincides with the beginning of the time interval associated with the context resource, and ends after the end of the time interval associated with the context resource",[Time Ontology in OWL] section 4.2.35 */

    public final static String linkrel_item = "item";
    /* The target IRI points to a resource that is a member of the collection represented by the context IRI.,[RFC6573] */

    public final static String linkrel_last = "last";
    /* An IRI that refers to the furthest following resource
    in a series of resources.",[RFC8288],"This relation type registration did not indicate a
    reference. Originally requested by Mark Nottingham in December
      2004." */

    public final static String linkrel_latestVersion = "latest-version";
    /* Points to a resource containing the latest (e.g.,
       current) version of the context.",[RFC5829] */

    public final static String linkrel_license = "license";
    /* Refers to a license associated with this context.,[RFC4946],"For implications of use in HTML, see: http://www.w3.org/TR/html5/links.html#link-type-license" */

    public final static String linkrel_lrdd = "lrdd";
    /* "Refers to further information about the link's context,
    expressed as a LRDD (""Link-based Resource Descriptor Document"")
    resource.  See [RFC6415] for information about
    processing this relation type in host-meta documents. When used
    elsewhere, it refers to additional links and other metadata.
    Multiple instances indicate additional LRDD resources. LRDD
    resources MUST have an ""application/xrd+xml"" representation, and
    MAY have others.",[RFC6415] */

    public final static String linkrel_manifest = "manifest";
    /* Links to a manifest file for the context.,[Web App Manifest] */

    public final static String linkrel_maskIcon = "mask-icon";
    /* Refers to a mask that can be applied to the icon for the context.,[Creating Pinned Tab Icons] */

    public final static String linkrel_mediaFeed = "media-feed";
    /* Refers to a feed of personalised media recommendations relevant to the link context.,[https://wicg.github.io/media-feeds/#discovery-of-media-feeds] */

    public final static String linkrel_memento = "memento";
    /* "The Target IRI points to a Memento, a fixed resource that will not change state anymore.",[RFC7089],"A Memento for an Original Resource is a resource that
    encapsulates a prior state of the Original Resource." */

    public final static String linkrel_micropub = "micropub";
    /* Links to the context's Micropub endpoint.,[Micropub] */

    public final static String linkrel_modulepreload = "modulepreload";
    /* Refers to a module that the user agent is to preemptively fetch and store for use in the current context.,[HTML] */

    public final static String linkrel_monitor = "monitor";
    /* Refers to a resource that can be used to monitor changes in an HTTP resource.,[RFC5989] */

    public final static String linkrel_monitorGroup = "monitor-group";
    /* Refers to a resource that can be used to monitor changes in a specified group of HTTP resources.,[RFC5989] */

    public final static String linkrel_next = "next";
    /* "Indicates that the link's context is a part of a series, and
    that the next in the series is the link target.",[HTML] */

    public final static String linkrel_nextArchive = "next-archive";

    /* Refers to the immediately following archive resource.,[RFC5005] */

    public final static String linkrel_nofollow = "nofollow";
    /* Indicates that the context’s original author or publisher does not endorse the link target.,[HTML] */

    public final static String linkrel_noopener = "noopener";
    /* Indicates that any newly created top-level browsing context which results from following the link will not be an auxiliary browsing context.,[HTML] */

    public final static String linkrel_noreferrer = "noreferrer";
    /* Indicates that no referrer information is to be leaked when following the link.,[HTML] */

    public final static String linkrel_opener = "opener";
    /* Indicates that any newly created top-level browsing context which results from following the link will be an auxiliary browsing context.,[HTML] */

    public final static String linkrel_openid2Local_id = "openid2.local_id";
    /* Refers to an OpenID Authentication server on which the context relies for an assertion that the end user controls an Identifier.,[OpenID Authentication 2.0 - Final] */

    public final static String linkrel_openid2Provider = "openid2.provider";
    /* Refers to a resource which accepts OpenID Authentication
       protocol messages for the context.,[OpenID Authentication 2.0 - Final] */

    public final static String linkrel_original = "original";
    /* The Target IRI points to an Original Resource.,[RFC7089],"An Original Resource is a resource that exists or used to
    exist";   /*  and for which access to one of its prior states may be
    required."
    P3Pv1,Refers to a P3P privacy policy for the context.,[The Platform for Privacy Preferences 1.0 (P3P1.0) Specification] */

    public final static String linkrel_payment = "payment";
    /* Indicates a resource where payment is accepted.,[RFC8288],

      This relation type registration did not indicate a
      reference.  Requested by Joshua Kinberg and Robert Sayre.  It is
      meant as a general way to facilitate acts of payment, and thus
      this specification makes no assumptions on the type of payment or
      transaction protocol.  Examples may include a web page where
      donations are accepted or where goods and services are available
      for purchase. rel=""payment"" is not intended to initiate an
      automated transaction.  In Atom documents, a link element with a
      rel=""payment" attribute may exist at the feed/channel level and/or
      the entry/item level.  For example, a rel=""payment"" link at the
      feed/channel level may point to a ""tip jar"" URI, whereas an entry/
      item containing a book review may include a rel=""payment"" link
      that points to the location where the book may be purchased
      through an online retailer. */

    public final static String linkrel_pingback = "pingback";
    /* Gives the address of the pingback resource for the link context.,[Pingback 1.0] */

    public final static String linkrel_preconnect = "preconnect";
    /* Used to indicate an origin that will be used to fetch
       required resources for the link context. Initiating an early
       connection, which includes the DNS lookup, TCP handshake, and
       optional TLS negotiation, allows the
       user sun.management.resources.agent to mask the high latency
       costs of establishing a connection.",[Resource Hints] */

    public final static String linkrel_predecessorVersion = "predecessor-version";
    /* "Points to a resource containing the predecessor
    version in the version history.",[RFC5829] */

    public final static String linkrel_prefetch = "prefetch";
    /* The prefetch link relation type is used to identify a resource
       that might be required by the next navigation from the link context,
       and that the user agent ought to fetch, such that the user agent
       can deliver a faster response once the resource is requested
       in the future.",[Resource Hints] */

    public final static String linkrel_preload = "preload";
    /* "Refers to a resource that should be loaded early in the processing of the link's context, without blocking rendering.",[Preload],Additional target attributes establish the detailed fetch properties of the link. */

    public final static String linkrel_prerender = "prerender";
    /* "Used to identify a resource that might be required by the next navigation from the link context, and that the user agent ought to fetch and execute, such that the user agent can deliver a faster response once the resource is requested in the future.",[Resource Hints] */

    public final static String linkrel_prev = "prev";
    /* "Indicates that the link's context is a part of a series, and
    that the previous in the series is the link target.",[HTML] */

    public final static String linkrel_preview = "preview";
    /* Refers to a resource that provides a preview of the link's context.,"[RFC6903], section 3" */

    public final static String linkrel_previous = "previous";
    /* "Refers to the previous resource in an ordered series
    of resources.  Synonym for ""prev"".",[HTML 4.01 Specification] */

    public final static String linkrel_prevArchive = "prev-archive";
    /* Refers to the immediately preceding archive resource.,[RFC5005] */

    public final static String linkrel_privacyPolicy = "privacy-policy";
    /* Refers to a privacy policy associated with the link's context.,"[RFC6903], section 4 */

    public final static String linkrel_profile = "profile";
    /* "Identifying that a resource representation conforms
    to a certain profile, without affecting the non-profile semantics
    of the resource representation.",[RFC6906],"Profile URIs are primarily intended to be used as
    identifiers, and thus clients SHOULD NOT indiscriminately access
    profile URIs. */

    public final static String linkrel_publication = "publication";
    /* "Links to a publication manifest. A manifest represents structured information about a publication, such as informative metadata, a list of resources, and a default reading order.",[Publication Manifest] */

    public final static String linkrel_related = "related";
    /* Identifies a related resource.,[RFC4287] */

    public final static String linkrel_restconf = "restconf";
    /* "Identifies the root of RESTCONF API as configured on this HTTP server.
    The ""restconf"" relation defines the root of the API defined in RFC8040.
    Subsequent revisions of RESTCONF will use alternate relation values to support protocol versioning.",[RFC8040] */

    public final static String linkrel_replies = "replies";
    /* "Identifies a resource that is a reply to the context
    of the link.",[RFC4685] */

    public final static String linkrel_ruleinput = "ruleinput";
    /* "The resource identified by the link target provides an input value to an instance of a rule, where the resource which represents the rule instance is identified by the link context.",[OCF Core Optional 2.2.0] */

    public final static String linkrel_search = "search";
    /* "Refers to a resource that can be used to search through
    the link's context and related resources.",[OpenSearch] */

    public final static String linkrel_section = "section";
    /* Refers to a section in a collection of resources.,[HTML 4.01 Specification] */

    public final static String linkrel_self = "self";
    /* Conveys an identifier for the link's context.,[RFC4287] */

    public final static String linkrel_service = "service";
    /* "Indicates a URI that can be used to retrieve a
    service document.",[RFC5023],"When used in an Atom document, this relation type specifies
    Atom Publishing Protocol service documents by default.  Requested
    by James Snell. */

    public final static String linkrel_serviceDesc = "service-desc";
    /* "Identifies service description for the context that
    is primarily intended for consumption by machines.",[RFC8631] */

    public final static String linkrel_serviceDoc = "service-doc";
    /* "Identifies service documentation for the context that
    is primarily intended for human consumption.",[RFC8631] */

    public final static String linkrel_serviceMeta = "service-meta";
    /* "Identifies general metadata for the context that is
    primarily intended for consumption by machines.",[RFC8631] */

    public final static String linkrel_sponsored = "sponsored";
    /* Refers to a resource that is within a context that is sponsored (such as advertising or another compensation agreement).,[Google Blog post 09-2019] */

    public final static String linkrel_start = "start";
    /* "Refers to the first resource in a collection of
    resources.",[HTML 4.01 Specification] */

    public final static String linkrel_status = "status";
    /* "Identifies a resource that represents the context's
    status.",[RFC8631] */

    public final static String linkrel_stylesheet = "stylesheet";
    /* Refers to a stylesheet.,[HTML] */

    public final static String linkrel_subsection = "subsection";
    /* "Refers to a resource serving as a subsection in a
    collection of resources.",[HTML 4.01 Specification] */

    public final static String linkrel_successorVersion = "successor-version";
    /* "Points to a resource containing the successor version
    in the version history.",[RFC5829] */

    public final static String linkrel_sunset = "sunset";
    /* "Identifies a resource that provides information about
    the context's retirement policy.",[RFC8594] */

    public final static String linkrel_tag = "tag";
    /* "Gives a tag (identified by the given address) that applies to
    the current document.",[HTML] */

    public final static String linkrel_termsOfService = "terms-of-service";
    /* Refers to the terms of service associated with the link's context.,"[RFC6903], section 5 */

    public final static String linkrel_timegate = "timegate";
    /* The Target IRI points to a TimeGate for an Original Resource.,[RFC7089],"A TimeGate for an Original Resource is a resource that is
    capable of datetime negotiation to support access to prior states
    of the Original Resource. */

    public final static String linkrel_timemap = "timemap";
    /* The Target IRI points to a TimeMap for an Original Resource.,[RFC7089],"A TimeMap for an Original Resource is a resource from which
    a list of URIs of Mementos of the Original Resource is available. */

    public final static String linkrel_type = "type";
    /* Refers to a resource identifying the abstract semantic type of which the link's context is considered to be an instance.,"[RFC6903], section 6 */

    public final static String linkrel_ugc = "ugc";
    /* Refers to a resource that is within a context that is User Generated Content.,[Google Blog post 09-2019] */

    public final static String linkrel_up = "up";
    /* "Refers to a parent document in a hierarchy of
    documents.",[RFC8288],"This relation type registration did not indicate a
    reference.  Requested by Noah Slater. */

    public final static String linkrel_versionHistory = "version-history";
    /* "Points to a resource containing the version history
            for the context.",[RFC5829] */

    public final static String linkrel_via = "via";
    /* "Identifies a resource that is the source of the
    information in the link's context.",[RFC4287] */

    public final static String linkrel_webmention = "webmention";
    /* "Identifies a target URI that supports the Webmention protocol.
      This allows clients that mention a resource in some form of publishing process
      to contact that endpoint and inform it that this resource has been mentioned.",
      [Webmention],"This is a similar ""Linkback"" mechanism to the ones of Refback,
      Trackback, and Pingback.

      It uses a different protocol, though, and thus should be
      discoverable through its own link
      relation type. */

    public final static String linkrel_workingCopy = "working-copy";
    /* Points to a working copy for this resource.,[RFC5829] */

    public final static String linkrel_workingCopyOf = "working-copy-of";
    /* "Points to the versioned resource from which this
    working copy was obtained.",[RFC5829] */

    private String value;

    /**
     * @param aValue a string representation of a relationship type
     */
    public Linkrel(final String aValue) {
        super(LINKREL, new Factory());
        this.value = Strings.unquote(aValue);
    }

    /**
     * {@inheritDoc}
     */
    public final String getValue() {
        return value;
    }

    public static class Factory extends Content.Factory
            implements ParameterFactory {
        private static final long serialVersionUID = 1L;

        public Factory() {
            super(LINKREL);
        }

        public Parameter createParameter(final String value)
                throws URISyntaxException {
            return new Linkrel(value);
        }
    }

}
