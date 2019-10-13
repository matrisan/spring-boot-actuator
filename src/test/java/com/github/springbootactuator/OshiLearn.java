package com.github.springbootactuator;

import org.junit.Test;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.util.Util;

import java.util.Arrays;

import static oshi.hardware.CentralProcessor.TickType.IDLE;
import static oshi.hardware.CentralProcessor.TickType.IOWAIT;
import static oshi.hardware.CentralProcessor.TickType.IRQ;
import static oshi.hardware.CentralProcessor.TickType.NICE;
import static oshi.hardware.CentralProcessor.TickType.SOFTIRQ;
import static oshi.hardware.CentralProcessor.TickType.STEAL;
import static oshi.hardware.CentralProcessor.TickType.SYSTEM;
import static oshi.hardware.CentralProcessor.TickType.USER;

/**
 * <p>
 * 创建时间为 15:25 2019-04-29
 * 项目名称 spring-boot-actuator
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

public class OshiLearn {

    @Test
    public void main() {
        SystemInfo systemInfo = new SystemInfo();
        System.out.println("系统:" + systemInfo.getOperatingSystem());
        OperatingSystem system = systemInfo.getOperatingSystem();
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        System.out.println("-------------------------");
//        printProcessor(hal);
        System.out.println("-------------------------");
        printCpu(hal);
        System.out.println("-------------------------");
//        printMemory(hal);
        System.out.println("-------------------------");
//        printSensors(hal);
        System.out.println("-------------------------");
        System.out.println("version:" + hal.getComputerSystem().getBaseboard().getVersion());
        System.out.println("-------------------------");
//        printDisk(hal);
        System.out.println("-------------------------");
//        printNetworkParameters(system);
        System.out.println("-------------------------");
//        printNetworkInterfaces(hal);
        System.out.println("-------------------------");
//        printFileSystem(system);
//        System.out.println("-------------------------");
//        printProcesses(system, hal.getMemory());
//        System.out.println("-------------------------");

    }

    private void printCpu(HardwareAbstractionLayer hal) {
        CentralProcessor processor = hal.getProcessor();
        System.out.println("Uptime: " + FormatUtil.formatElapsedSecs(processor.getSystemUptime()));
        System.out.println("Context Switches/Interrupts: " + processor.getContextSwitches() + " / " + processor.getInterrupts());

        long[] prevTicks = processor.getSystemCpuLoadTicks();
        System.out.println("CPU, IOWait, and IRQ ticks @ 0 sec:" + Arrays.toString(prevTicks));
        // Wait a second...
        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        System.out.println("CPU, IOWait, and IRQ ticks @ 1 sec:" + Arrays.toString(ticks));
        long user = ticks[USER.getIndex()] - prevTicks[USER.getIndex()];
        long nice = ticks[NICE.getIndex()] - prevTicks[NICE.getIndex()];
        long sys = ticks[SYSTEM.getIndex()] - prevTicks[SYSTEM.getIndex()];
        long idle = ticks[IDLE.getIndex()] - prevTicks[IDLE.getIndex()];
        long iowait = ticks[IOWAIT.getIndex()] - prevTicks[IOWAIT.getIndex()];
        long irq = ticks[IRQ.getIndex()] - prevTicks[IRQ.getIndex()];
        long softirq = ticks[SOFTIRQ.getIndex()] - prevTicks[SOFTIRQ.getIndex()];
        long steal = ticks[STEAL.getIndex()] - prevTicks[STEAL.getIndex()];
        long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;

        System.out.format(
                "User: %.1f%% Nice: %.1f%% System: %.1f%% Idle: %.1f%% IOwait: %.1f%% IRQ: %.1f%% SoftIRQ: %.1f%% Steal: %.1f%%%n",
                100d * user / totalCpu,
                100d * nice / totalCpu,
                100d * sys / totalCpu,
                100d * idle / totalCpu,
                100d * iowait / totalCpu,
                100d * irq / totalCpu,
                100d * softirq / totalCpu,
                100d * steal / totalCpu);
        System.out.format("CPU load: %.1f%% (counting ticks)%n", processor.getSystemCpuLoadBetweenTicks() * 100);
        System.out.format("CPU load: %.1f%% (OS MXBean)%n", processor.getSystemCpuLoad() * 100);
        double[] loadAverage = processor.getSystemLoadAverage(3);
        System.out.println("CPU load averages:" + (loadAverage[0] < 0 ? " N/A" : String.format(" %.2f", loadAverage[0]))
                + (loadAverage[1] < 0 ? " N/A" : String.format(" %.2f", loadAverage[1]))
                + (loadAverage[2] < 0 ? " N/A" : String.format(" %.2f", loadAverage[2])));
        // per core CPU
        StringBuilder procCpu = new StringBuilder("CPU load per processor:");
        double[] load = processor.getProcessorCpuLoadBetweenTicks();
        for (double avg : load) {
            procCpu.append(String.format(" %.1f%%", avg * 100));
        }
        System.out.println(procCpu.toString());
    }

//    private static void printProcessor(HardwareAbstractionLayer hal) {
//        CentralProcessor processor = hal.getProcessor();
//        System.out.println(processor);
//        System.out.println(" " + processor.getPhysicalPackageCount() + " physical CPU package(s)");
//        System.out.println(" " + processor.getPhysicalProcessorCount() + " physical CPU core(s)");
//        System.out.println(" " + processor.getLogicalProcessorCount() + " logical CPU(s)");
//
//        System.out.println("Identifier: " + processor.getIdentifier());
//        System.out.println("ProcessorID: " + processor.getProcessorID());
//    }

//    private static void printMemory(HardwareAbstractionLayer hal) {
//        GlobalMemory memory = hal.getMemory();
//        System.out.println("Memory: " + FormatUtil.formatBytes(memory.getAvailable()) + "/" + FormatUtil.formatBytes(memory.getTotal()));
//        System.out.println("Swap used: " + FormatUtil.formatBytes(memory.getSwapUsed()) + "/" + FormatUtil.formatBytes(memory.getSwapTotal()));
//    }

//    private static void printSensors(HardwareAbstractionLayer hal) {
//        Sensors sensors = hal.getSensors();
//        System.out.println("Sensors:");
//        System.out.format(" CPU Temperature: %.1f°C%n", sensors.getCpuTemperature());
//        System.out.println(" Fan Speeds: " + Arrays.toString(sensors.getFanSpeeds()));
//        System.out.format(" CPU Voltage: %.1fV%n", sensors.getCpuVoltage());
//    }

//    private void printDisk(HardwareAbstractionLayer hal) {
//        HWDiskStore[] diskStores = hal.getDiskStores();
//        System.out.println("Disks:");
//        System.out.println("                           ");
//        System.out.println(JSON.toJSONString(diskStores));
//        System.out.println("                           ");
//        for (HWDiskStore disk : diskStores) {
//            boolean readwrite = disk.getReads() > 0 || disk.getWrites() > 0;
//            System.out.format(" %s: (model: %s - S/N: %s) size: %s, reads: %s (%s), writes: %s (%s), xfer: %s ms%n",
//
//                    disk.getName(),
//                    disk.getModel(),
//                    disk.getSerial(),
//                    disk.getSize() > 0 ? FormatUtil.formatBytesDecimal(disk.getSize()) : "?",
//                    readwrite ? disk.getReads() : "?",
//                    readwrite ? FormatUtil.formatBytes(disk.getReadBytes()) : "?",
//                    readwrite ? disk.getWrites() : "?",
//                    readwrite ? FormatUtil.formatBytes(disk.getWriteBytes()) : "?",
//                    readwrite ? disk.getTransferTime() : "?");
//
//            HWPartition[] partitions = disk.getPartitions();
//            if (partitions == null) {
//                // TODO Remove when all OS's implemented
//                continue;
//            }
//            for (HWPartition part : partitions) {
//                System.out.format(" |-- %s: %s (%s) Maj:Min=%d:%d, size: %s%s%n", part.getIdentification(),
//                        part.getName(), part.getType(), part.getMajor(), part.getMinor(),
//                        FormatUtil.formatBytesDecimal(part.getSize()),
//                        part.getMountPoint().isEmpty() ? "" : " @ " + part.getMountPoint());
//            }
//        }
//    }

//    private void printNetworkParameters(OperatingSystem system) {
//        NetworkParams networkParams = system.getNetworkParams();
//        System.out.println("Network parameters:");
//        System.out.format(" Host name: %s%n", networkParams.getHostName());
//        System.out.format(" Domain name: %s%n", networkParams.getDomainName());
//        System.out.format(" DNS servers: %s%n", Arrays.toString(networkParams.getDnsServers()));
//        System.out.format(" IPv4 Gateway: %s%n", networkParams.getIpv4DefaultGateway());
//        System.out.format(" IPv6 Gateway: %s%n", networkParams.getIpv6DefaultGateway());
//    }

//    private void printNetworkInterfaces(HardwareAbstractionLayer hal) {
//
//        NetworkIF[] networkIFs = hal.getNetworkIFs();
//        System.out.println("Network interfaces:");
//        for (NetworkIF net : networkIFs) {
//            System.out.format(" Name: %s (%s)%n", net.getName(), net.getDisplayName());
//            System.out.format("   MAC Address: %s %n", net.getMacaddr());
//            System.out.format("   MTU: %s, Speed: %s %n", net.getMTU(), FormatUtil.formatValue(net.getSpeed(), "bps"));
//            System.out.format("   IPv4: %s %n", Arrays.toString(net.getIPv4addr()));
//            System.out.format("   IPv6: %s %n", Arrays.toString(net.getIPv6addr()));
//            boolean hasData = net.getBytesRecv() > 0 || net.getBytesSent() > 0 || net.getPacketsRecv() > 0
//                    || net.getPacketsSent() > 0;
//            System.out.format("   Traffic: received %s/%s%s; transmitted %s/%s%s %n",
//                    hasData ? net.getPacketsRecv() + " packets" : "?",
//                    hasData ? FormatUtil.formatBytes(net.getBytesRecv()) : "?",
//                    hasData ? " (" + net.getInErrors() + " err)" : "",
//                    hasData ? net.getPacketsSent() + " packets" : "?",
//                    hasData ? FormatUtil.formatBytes(net.getBytesSent()) : "?",
//                    hasData ? " (" + net.getOutErrors() + " err)" : "");
//
//        }
//    }
//
//    private void printFileSystem(OperatingSystem system) {
//        FileSystem fileSystem = system.getFileSystem();
//
//        System.out.println("File System:");
//        System.out.format(" File Descriptors: %d/%d%n", fileSystem.getOpenFileDescriptors(),
//                fileSystem.getMaxFileDescriptors());
//
//        OSFileStore[] fsArray = fileSystem.getFileStores();
//        for (OSFileStore fs : fsArray) {
//            long usable = fs.getUsableSpace();
//            long total = fs.getTotalSpace();
//            System.out.format(
//                    " %s (%s) [%s] %s of %s free (%.1f%%) is %s "
//                            + (fs.getLogicalVolume() != null && fs.getLogicalVolume().length() > 0 ? "[%s]" : "%s")
//                            + " and is mounted at %s%n",
//                    fs.getName(), fs.getDescription().isEmpty() ? "file system" : fs.getDescription(), fs.getType(),
//                    FormatUtil.formatBytes(usable), FormatUtil.formatBytes(fs.getTotalSpace()), 100d * usable / total,
//                    fs.getVolume(), fs.getLogicalVolume(), fs.getMount());
//        }
//    }
//
//    private void printProcesses(OperatingSystem os, GlobalMemory memory) {
//        System.out.println("Processes: " + os.getProcessCount() + ", Threads: " + os.getThreadCount());
//        // Sort by highest CPU
//        List<OSProcess> procs = Arrays.asList(os.getProcesses(5, OperatingSystem.ProcessSort.CPU));
//        System.out.println("   PID  %CPU %MEM       VSZ       RSS Name");
//        for (int i = 0; i < procs.size() && i < 5; i++) {
//            OSProcess p = procs.get(i);
//            System.out.format(" %5d %5.1f %4.1f %9s %9s %s%n", p.getProcessID(),
//                    100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime(),
//                    100d * p.getResidentSetSize() / memory.getTotal(), FormatUtil.formatBytes(p.getVirtualSize()),
//                    FormatUtil.formatBytes(p.getResidentSetSize()), p.getName());
//        }
//    }


//    private void printDisplays(Display[] displays) {
//        System.out.println("Displays:");
//        int i = 0;
//        for (Display display : displays) {
//            System.out.println(" Display " + i + ":");
//            System.out.println(display.toString());
//            i++;
//        }
//    }
//
//    private void printUsbDevices(UsbDevice[] usbDevices) {
//        System.out.println("USB Devices:");
//        for (UsbDevice usbDevice : usbDevices) {
//            System.out.println(usbDevice.toString());
//        }
//    }

}

