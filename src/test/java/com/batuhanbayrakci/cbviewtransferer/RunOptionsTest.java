package com.batuhanbayrakci.cbviewtransferer;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class RunOptionsTest {

    @Test
    public void shouldCreateRunOptionsFromArgsForNodeAddresses() throws ParseException {
        RunOptions runOptions = RunOptions.createFrom(new String[]{
                "-buckets", "Customers,Orders",
                "-source", "http://111.11.11.11:8091",
                "-target", "http://222.22.22.22:8091",
                "-username", "admin", "-password", "123456"});

        assertThat(runOptions.getBucketNames(), is(Arrays.asList("Customers", "Orders")));
        assertThat(runOptions.getSourceURI().toString(), equalTo("http://111.11.11.11:8091/pools"));
        assertThat(runOptions.getTargetURI().toString(), equalTo("http://222.22.22.22:8091/pools"));
        assertThat(runOptions.getUsername(), equalTo("admin"));
        assertThat(runOptions.getPassword(), equalTo("123456"));
    }

    @Test
    public void shouldCreateRunOptionsFromArgsForNodeAddressesWithoutBuckets() throws ParseException {
        RunOptions runOptions = RunOptions.createFrom(new String[]{
                "-source", "http://111.11.11.11:8091",
                "-target", "http://222.22.22.22:8091",
                "-username", "admin", "-password", "123456"});

        assertThat(runOptions.getBucketNames().size(), equalTo(0));
        assertThat(runOptions.getSourceURI().toString(), equalTo("http://111.11.11.11:8091/pools"));
        assertThat(runOptions.getTargetURI().toString(), equalTo("http://222.22.22.22:8091/pools"));
        assertThat(runOptions.getUsername(), equalTo("admin"));
        assertThat(runOptions.getPassword(), equalTo("123456"));
    }

    @Test
    public void shouldCreateRunOptionsFromArgsForLocalSourceFileAddressWithTargetNodeAddress() throws ParseException {
        RunOptions runOptions = RunOptions.createFrom(new String[]{
                "-buckets", "Customers,Orders",
                "-source", "/Users/berbat/views.xml",
                "-target", "http://222.22.22.22:8091/pools",
                "-username", "admin", "-password", "123456"});

        assertThat(runOptions.getBucketNames(), is(Arrays.asList("Customers", "Orders")));
        assertThat(runOptions.getSourceURI().toString(), equalTo("/Users/berbat/views.xml"));
        assertThat(runOptions.getTargetURI().toString(), equalTo("http://222.22.22.22:8091/pools"));
        assertThat(runOptions.getUsername(), equalTo("admin"));
        assertThat(runOptions.getPassword(), equalTo("123456"));
    }

    @Test
    public void shouldCreateRunOptionsFromArgsForLocalSourceNodeAddressWithTargetLocalFileAddress() throws ParseException {
        RunOptions runOptions = RunOptions.createFrom(new String[]{
                "-buckets", "Customers,Orders",
                "-source", "http://111.11.11.11:8091/",
                "-target", "/Users/berbat/views.xml",
                "-username", "admin", "-password", "123456"});

        assertThat(runOptions.getBucketNames(), is(Arrays.asList("Customers", "Orders")));
        assertThat(runOptions.getSourceURI().toString(), equalTo("http://111.11.11.11:8091/pools"));
        assertThat(runOptions.getTargetURI().toString(), equalTo("/Users/berbat/views.xml"));
        assertThat(runOptions.getUsername(), equalTo("admin"));
        assertThat(runOptions.getPassword(), equalTo("123456"));
    }

    @Test
    public void shouldCreateRunOptionsFromArgsForLocalFileAddresses() throws ParseException {
        RunOptions runOptions = RunOptions.createFrom(new String[]{
                "-buckets", "Customers,Orders",
                "-source", "/Users/berbat/source.xml",
                "-target", "/Users/berbat/target.xml",
                "-username", "admin", "-password", "123456"});

        assertThat(runOptions.getBucketNames(), is(Arrays.asList("Customers", "Orders")));
        assertThat(runOptions.getSourceURI().toString(), equalTo("/Users/berbat/source.xml"));
        assertThat(runOptions.getTargetURI().toString(), equalTo("/Users/berbat/target.xml"));
        assertThat(runOptions.getUsername(), equalTo("admin"));
        assertThat(runOptions.getPassword(), equalTo("123456"));
    }

    @Test
    public void shouldNotCreateRunOptionsFromArgsIfArgsIsEmpty() throws ParseException {
        assertThat(RunOptions.createFrom(new String[]{}), nullValue());
    }

    @Test
    public void shouldNotCreateRunOptionsFromArgsIfFirstElementOfArgsHasContain_help_Word() throws ParseException {
        assertThat(RunOptions.createFrom(new String[]{"--help", "a", "b", "c"}), nullValue());
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotCreateRunOptionsFromArgsIfSourceAndTargetAddressesAreSame() throws ParseException {
        try {
            RunOptions.createFrom(new String[]{
                    "-buckets", "Customers,Orders",
                    "-source", "http://111.11.11.11:8091/",
                    "-target", "http://111.11.11.11:8091/",
                    "-username", "admin", "-password", "123456"});
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("Source and target URIs cannot be same."));
            throw e;
        }
        fail();
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotCreateRunOptionsFromArgsIfUsernameOfSourceIsEmptyWhenSourceIsNodeAddress() throws ParseException {
        try {
            RunOptions.createFrom(new String[]{
                    "-buckets", "Customers,Orders",
                    "-source", "http://111.11.11.11:8091/",
                    "-target", "http://222.22.22.22:8091/",
                    "-password", "123456"});
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("Couchbase admin panel username and password is mandatory."));
            throw e;
        }
        fail();
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotCreateRunOptionsFromArgsIfPasswordOfSourceIsEmptyWhenSourceIsNodeAddress() throws ParseException {
        try {
            RunOptions.createFrom(new String[]{
                    "-buckets", "Customers,Orders",
                    "-source", "http://111.11.11.11:8091/",
                    "-target", "http://222.22.22.22:8091/",
                    "-username", "admin"});
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("Couchbase admin panel username and password is mandatory."));
            throw e;
        }
        fail();
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotCreateRunOptionsFromArgsIfUsernameAndPasswordOfSourceAreEmptyWhenSourceIsNodeAddress() throws ParseException {
        try {
            RunOptions.createFrom(new String[]{
                    "-buckets", "Customers,Orders",
                    "-source", "http://111.11.11.11:8091/",
                    "-target", "http://222.22.22.22:8091/"});
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("Couchbase admin panel username and password is mandatory."));
            throw e;
        }
        fail();
    }

    @Test
    public void shouldCreateRunOptionsFromArgsForLocalSourceFileAddressWithTargetNodeAddressEvenThereAreNoUsernameAndPasswordArgs() throws ParseException {
        RunOptions runOptions = RunOptions.createFrom(new String[]{
                "-buckets", "Customers,Orders",
                "-source", "/Users/berbat/views.xml",
                "-target", "http://222.22.22.22:8091"});

        assertThat(runOptions.getBucketNames(), is(Arrays.asList("Customers", "Orders")));
        assertThat(runOptions.getSourceURI().toString(), equalTo("/Users/berbat/views.xml"));
        assertThat(runOptions.getTargetURI().toString(), equalTo("http://222.22.22.22:8091/pools"));
        assertThat(runOptions.getUsername(), equalTo(""));
        assertThat(runOptions.getPassword(), equalTo(""));
    }

}