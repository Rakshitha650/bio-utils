package io.mosip.biometrics.util.iris;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

import io.mosip.biometrics.util.AbstractImageInfo;

public class Representation extends AbstractImageInfo {
	private RepresentationHeader representationHeader;
	private RepresentationData representationData;

	public Representation(Date captureDate, IrisQualityBlock[] qualityBlocks, ImageInformation imageInformation,
			int representationNo, byte[] image) {
		setRepresentationData(new RepresentationData(new ImageData(image)));
		setRepresentationHeader(new RepresentationHeader(getRepresentationData().getRecordLength(), captureDate,
				qualityBlocks, imageInformation, representationNo));
	}

	public Representation(Date captureDate, int sourceType, int deviceVendor, int deviceType,
			IrisQualityBlock[] qualityBlocks, ImageInformation imageInformation, int representationNo, byte[] image) {
		setRepresentationData(new RepresentationData(new ImageData(image)));
		setRepresentationHeader(new RepresentationHeader(getRepresentationData().getRecordLength(), captureDate,
				sourceType, deviceVendor, deviceType, qualityBlocks, imageInformation, representationNo));
	}

	public Representation(DataInputStream inputStream) throws IOException {
		readObject(inputStream);
	}

	public Representation(DataInputStream inputStream, boolean onlyImageInformation) throws IOException {
		readObject(inputStream, onlyImageInformation);
	}

	@Override
	protected void readObject(DataInputStream inputStream) throws IOException {
		setRepresentationHeader(new RepresentationHeader(inputStream));
		setRepresentationData(new RepresentationData(inputStream));
	}

	@Override
	protected void readObject(DataInputStream inputStream, boolean onlyImageInformation) throws IOException {
		setRepresentationHeader(new RepresentationHeader(inputStream, onlyImageInformation));
		setRepresentationData(new RepresentationData(inputStream, onlyImageInformation));
	}

	@Override
	public long getRecordLength() {
		return getRepresentationHeader().getRecordLength() + getRepresentationData().getRecordLength();
	}

	@Override
	public void writeObject(DataOutputStream outputStream) throws IOException {
		getRepresentationHeader().writeObject(outputStream);
		getRepresentationData().writeObject(outputStream);
		outputStream.flush();
	}

	public RepresentationHeader getRepresentationHeader() {
		return representationHeader;
	}

	public void setRepresentationHeader(RepresentationHeader representationHeader) {
		this.representationHeader = representationHeader;
	}

	public RepresentationData getRepresentationData() {
		return representationData;
	}

	public void setRepresentationData(RepresentationData representationData) {
		this.representationData = representationData;
	}

	@Override
	public String toString() {
		return "\nRepresentation [RepresentationRecordLength=" + getRecordLength() + ", representationHeader="
				+ representationHeader + ", representationData=" + representationData + "]\n";
	}
}
